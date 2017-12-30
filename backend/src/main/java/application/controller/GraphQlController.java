package application.controller;

import application.query.annotated.PersonQuery;
import graphql.*;
import graphql.language.SourceLocation;
import graphql.schema.GraphQLSchema;
import io.leangen.graphql.GraphQLSchemaGenerator;
import io.leangen.graphql.metadata.strategy.query.AnnotatedResolverBuilder;
import io.leangen.graphql.metadata.strategy.query.PublicResolverBuilder;
import io.leangen.graphql.metadata.strategy.value.jackson.JacksonValueMapperFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class GraphQlController {
  private static final Logger LOGGER = LoggerFactory.getLogger(GraphQlController.class);

  private final GraphQL graphQlFromAnnotated;

  @Autowired
  public GraphQlController(PersonQuery personQuery) {

    //Schema generated from query classes
    GraphQLSchema schemaFromAnnotated = new GraphQLSchemaGenerator()
      .withResolverBuilders(
        //Resolve by annotations
        new AnnotatedResolverBuilder(),
        //Resolve public methods inside root package
        new PublicResolverBuilder("application"))
      .withOperationsFromSingleton(personQuery)
      .withValueMapperFactory(new JacksonValueMapperFactory())
      .generate();
    graphQlFromAnnotated = GraphQL.newGraphQL(schemaFromAnnotated).build();
    LOGGER.info("Generated GraphQL schema using SPQR");
  }

  @PostMapping(value = "/graphql", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @ResponseBody
  public Object indexFromAnnotated(@RequestBody Map<String, Object> request) {

    ExecutionInput executionInput = ExecutionInput.newExecutionInput()
      .query((String) request.get("query"))
      .operationName((String) request.get("operationName"))
      .context(null)
      .root(null) // This we are doing do be backwards compatible
      .build();
    ExecutionResult executionResult = graphQlFromAnnotated.execute(executionInput);

    if (!executionResult.getErrors().isEmpty()) {
      return sanitize(executionResult);
    }

    return executionResult;
  }

  //Just mocking error handling
  private ExecutionResult sanitize(ExecutionResult executionResult) {
    return new ExecutionResultImpl(executionResult.getErrors().stream()
      .peek(err -> LOGGER.error(err.getMessage()))
      .map(this::sanitize)
      .collect(Collectors.toList()));
  }

  private GraphQLError sanitize(GraphQLError error) {
    if (error instanceof ExceptionWhileDataFetching) {
      return new GraphQLError() {
        @Override
        public String getMessage() {
          Throwable cause = ((ExceptionWhileDataFetching) error).getException().getCause();
          return cause instanceof InvocationTargetException ? ((InvocationTargetException) cause).getTargetException().getMessage() : cause.getMessage();
        }

        @Override
        public List<SourceLocation> getLocations() {
          return error.getLocations();
        }

        @Override
        public ErrorType getErrorType() {
          return error.getErrorType();
        }
      };
    }
    return error;
  }
}

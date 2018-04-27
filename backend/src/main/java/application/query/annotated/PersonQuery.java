package application.query.annotated;

import application.model.Person;
import application.repo.PersonRepository;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class PersonQuery {

  @Autowired
  private PersonRepository personRepository;

  /**
   * Hello world greeting.
   * <p>
   * Invoke with:
   * {greeting(Person: {firstName: "John", lastName: "Doe"})}
   *
   * @param person Person to greet
   * @return Hello string
   */
  @GraphQLQuery(name = "greeting")
  public String getGreeting(@GraphQLArgument(name = "Person", description = "Person to greet.") final Person person) {
    return "Hello " + person.getFirstName() + "!";
  }


  /**
   * Getting a list of people
   * <p>
   * Invoke with:
   * {people {id, firstName, lastName}}
   *
   * @return List<Person> people
   */
  @GraphQLQuery(name = "people")
  public List<Person> getPeople() {
    List<Person> people = personRepository.findAll();
    return StreamSupport.stream(people.spliterator(), false).collect(Collectors.toList());
  }
}

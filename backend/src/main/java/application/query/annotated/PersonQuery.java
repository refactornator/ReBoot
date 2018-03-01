package application.query.annotated;

import application.model.Person;
import application.repo.PersonRepository;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
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
   * {people(Person: {id: 1}) {firstName, lastName}}
   * {people(Person: {firstName: "Dot"}) {id}}
   * {people(Person: {lastName: "Matrix"}) {id, firstName}}
   *
   * @return people
   */
  @GraphQLQuery(name = "people")
  public List<Person> getPeople(@GraphQLArgument(name = "Person", description = "Find people by id, first name, or last name") Person person) {
    if(person == null) person = new Person();

    Example<Person> example = Example.of(person);
    List<Person> people = personRepository.findAll(example);
    return StreamSupport.stream(people.spliterator(), false).collect(Collectors.toList());
  }


  @GraphQLMutation(name = "addPerson")
  public Person addPerson(@GraphQLArgument(name = "Person", description = "Add a Person to the database") Person person) {
    Person savedPerson = personRepository.save(person);

    return savedPerson;
  }
}

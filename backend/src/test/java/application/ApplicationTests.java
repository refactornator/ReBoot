package application;

import application.model.Person;
import application.query.annotated.PersonQuery;
import application.repo.PersonRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.constraints.Null;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

  @Autowired
  private PersonQuery personQuery;

  @Autowired
  private PersonRepository personRepository;


  @Test
  public void contextLoads() {
  }

  @Test
  public void testGetGreeting() {
    Person person = new Person("Bob", "Testerson");
    assertEquals(personQuery.getGreeting(person), "Hello Bob!");
  }

  @Test
  public void testGetPeople() {
    List<Person> people = personQuery.getPeople(null);

    assertEquals(people.size(), 3);
  }

  @Test
  public void testGetBob() {
    Person person = new Person("Bob", null);
    List<Person> people = personQuery.getPeople(person);

    assertEquals(people.size(), 1);
    Person actualPerson = people.get(0);
    assertEquals(actualPerson.getFirstName(), "Bob");
  }

  @Test
  public void testAddPerson() {

    Person person = personQuery.addPerson("Mr Awesome", "Forever");


    assertEquals(person.getFirstName(), "Mr Awesome");
    assertEquals(person.getLastName(), "Forever");
    assertNotNull(person.getId());

    assertNotNull(personRepository.findOne(person.getId()));

  }

}

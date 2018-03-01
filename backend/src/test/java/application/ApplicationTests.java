package application;

import application.model.Person;
import application.query.annotated.PersonQuery;
import application.repo.PersonRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ApplicationTests {

  @Autowired
  private PersonRepository personRepository;

  @Autowired
  private PersonQuery personQuery;

  @Test
  public void contextLoads() {
  }

  @Test
  public void getGreetingTest() {
    Person person = new Person("Alex", "Bool");
    String result = personQuery.getGreeting(person);
    assertEquals("Hello Alex!", result);
  }

  @Test
  public void getPeopleTest() {
    List<Person> result = personQuery.getPeople(null);
    List<Person> expected = Arrays.asList(
      new Person(1L, "Dot", "Matrix"),
      new Person(2L, "Enzo", "Matrix"),
      new Person(3L, "Bob", null)
    );
    assertEquals(expected, result);
  }

  @Test
  public void addPersonTest() {
    Person person = new Person("Alex", "Bool");

    Person addedPerson = personQuery.addPerson(person);
    Person savedPerson = personRepository.findOne(addedPerson.getId());

    assertEquals(savedPerson.getFirstName(), "Alex");
    assertEquals(savedPerson.getLastName(), "Bool");

    assertEquals(addedPerson.getFirstName(), "Alex");
    assertEquals(addedPerson.getLastName(), "Bool");
  }

}

package application.query.annotated;

import application.model.Person;
import application.repo.PersonRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonQueryTests {

  @Autowired
  PersonRepository personRepository;

  @Autowired
  PersonQuery personQuery;

  @Before
  public void setUp() {
    personRepository.deleteAll();
  }

  @Test
  public void TestGetGreeting() {
    Person person = Person.builder().firstName("Rob").lastName("Mee").build();
    String actualGreeting = personQuery.getGreeting(person);
    assertThat(actualGreeting).isEqualTo("Hello Rob!");
  }

  @Test
  public void TestGetPeople() {
    List<Person> people = new ArrayList<>();
    Person person1 = Person.builder().firstName("Rob").lastName("Mee").build();
    Person person2 = Person.builder().firstName("Bat").lastName("Man").build();
    people.add(person1);
    people.add(person2);

    personRepository.save(people);

    List<Person> actualPeople = personQuery.getPeople();
    assertThat(actualPeople).isEqualTo(people);
  }

}

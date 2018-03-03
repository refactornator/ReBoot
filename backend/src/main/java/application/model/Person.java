package application.model;

import io.leangen.graphql.annotations.GraphQLQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Data
@Table(name = "PERSON")
public class Person {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @NotBlank
  @Size(max = 50)
  private String firstName;

  @Size(max = 50)
  private String lastName;

  public Person() {

  }

  @GraphQLQuery(name = "id")
  public Long getId() {
    return id;
  }

  @GraphQLQuery(name = "firstName")
  public String getFirstName() {
    return firstName;
  }

  @GraphQLQuery(name = "lastName")
  public String getLastName() {
    return lastName;
  }

  public Person(String firstName, String lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
  }
}

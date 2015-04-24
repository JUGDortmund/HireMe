package model;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity
public class Profile {

  @Id
  private ObjectId id;

  private String firstname;
  private String lastname;
  private String careerStage;

  public Profile() {
    firstname = "Klaus";
  }

  public String getFirstname() {
    return firstname;
  }

  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  public String getLastname() {
    return lastname;
  }

  public void setLastname(String lastname) {
    this.lastname = lastname;
  }

  public String getCareerStage() {
    return careerStage;
  }

  public void setCareerStage(String careerStage) {
    this.careerStage = careerStage;
  }
}

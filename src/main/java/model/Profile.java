package model;

import org.mongodb.morphia.annotations.Entity;

@Entity
public class Profile extends BaseModel{

  private String firstname;
  private String lastname;
  private String careerStage;

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

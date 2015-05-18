package model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Reference;

import util.serializer.ResourceDeserializer;
import util.serializer.ResourceSerializer;

import java.util.Date;

@Entity
public class Profile extends BaseModel {

  private String firstname;
  private String lastname;
  private String careerStage;
  private String degree;
  private Date workExperience;
  private String languages;
  private String industry;
  private String platforms;
  private String opSystems;
  private String progLanguages;
  private String webTechnologies;
  private String devEnvironments;
  private String qualifications;
  private String summary;

  @JsonSerialize(using = ResourceSerializer.class)
  @JsonDeserialize(using = ResourceDeserializer.class)
  @Reference("image")
  private Resource image;

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

  public String getDegree() {
    return degree;
  }

  public void setDegree(String degree) {
    this.degree = degree;
  }

  public Date getWorkExperience() {
    return workExperience;
  }

  public void setWorkExperience(Date workExperience) {
    this.workExperience = workExperience;
  }

  public String getLanguages() {
    return languages;
  }

  public void setLanguages(String languages) {
    this.languages = languages;
  }

  public String getIndustry() {
    return industry;
  }

  public void setIndustry(String industry) {
    this.industry = industry;
  }

  public String getPlatforms() {
    return platforms;
  }

  public void setPlatforms(String platforms) {
    this.platforms = platforms;
  }

  public String getOpSystems() {
    return opSystems;
  }

  public void setOpSystems(String opSystems) {
    this.opSystems = opSystems;
  }

  public String getProgLanguages() {
    return progLanguages;
  }

  public void setProgLanguages(String progLanguages) {
    this.progLanguages = progLanguages;
  }

  public String getWebTechnologies() {
    return webTechnologies;
  }

  public void setWebTechnologies(String webTechnologies) {
    this.webTechnologies = webTechnologies;
  }

  public String getDevEnvironments() {
    return devEnvironments;
  }

  public void setDevEnvironments(String devEnvironments) {
    this.devEnvironments = devEnvironments;
  }

  public String getQualifications() {
    return qualifications;
  }

  public void setQualifications(String qualifications) {
    this.qualifications = qualifications;
  }

  public String getSummary() {
    return summary;
  }

  public void setSummary(String summary) {
    this.summary = summary;
  }

  public Resource getImage() {
    return image;
  }

  public void setImage(Resource image) {
    this.image = image;
  }
}

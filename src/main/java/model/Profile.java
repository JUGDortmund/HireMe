package model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Reference;

import util.serializer.ResourceDeserializer;
import util.serializer.ResourceSerializer;

import java.util.Date;
import java.util.List;

import model.annotations.Tag;

@Entity
public class Profile extends BaseModel {

  private String firstname;

  private String lastname;

  @Tag
  private List<String> careerLevel;

  @Tag
  private List<String> degrees;

  private Date workExperience;

  @Tag
  private List<String> languages;

  @Tag
  private List<String> industries;

  @Tag
  private List<String> platforms;

  @Tag
  private List<String> opSystems;

  @Tag
  private List<String> progLanguages;

  @Tag
  private List<String> webTechnologies;

  @Tag
  private List<String> devEnvironments;

  @Tag
  private List<String> qualifications;

  private String summary;

  @Embedded
  private List<ProjectAssociation> projectAssociations;

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

  public List<String> getCareerLevel() {
    return careerLevel;
  }

  public void setCareerLevel(List<String> careerLevel) {
    this.careerLevel = careerLevel;
  }

  public List<String> getDegrees() {
    return degrees;
  }

  public void setDegrees(List<String> degrees) {
    this.degrees = degrees;
  }

  public Date getWorkExperience() {
    return workExperience;
  }

  public void setWorkExperience(Date workExperience) {
    this.workExperience = workExperience;
  }

  public List<String> getLanguages() {
    return languages;
  }

  public void setLanguages(List<String> languages) {
    this.languages = languages;
  }

  public List<String> getIndustries() {
    return industries;
  }

  public void setIndustries(List<String> industries) {
    this.industries = industries;
  }

  public List<String> getPlatforms() {
    return platforms;
  }

  public void setPlatforms(List<String> platforms) {
    this.platforms = platforms;
  }

  public List<String> getOpSystems() {
    return opSystems;
  }

  public void setOpSystems(List<String> opSystems) {
    this.opSystems = opSystems;
  }

  public List<String> getProgLanguages() {
    return progLanguages;
  }

  public void setProgLanguages(List<String> progLanguages) {
    this.progLanguages = progLanguages;
  }

  public List<String> getWebTechnologies() {
    return webTechnologies;
  }

  public void setWebTechnologies(List<String> webTechnologies) {
    this.webTechnologies = webTechnologies;
  }

  public List<String> getDevEnvironments() {
    return devEnvironments;
  }

  public void setDevEnvironments(List<String> devEnvironments) {
    this.devEnvironments = devEnvironments;
  }

  public List<String> getQualifications() {
    return qualifications;
  }

  public void setQualifications(List<String> qualifications) {
    this.qualifications = qualifications;
  }

  public String getSummary() {
    return summary;
  }

  public void setSummary(String summary) {
    this.summary = summary;
  }

  public List<ProjectAssociation> getProjectAssociations() {
    return projectAssociations;
  }

  public void setProjectAssociations(List<ProjectAssociation> projectAssociations) {
    this.projectAssociations = projectAssociations;
  }

  public Resource getImage() {
    return image;
  }

  public void setImage(Resource image) {
    this.image = image;
  }
}

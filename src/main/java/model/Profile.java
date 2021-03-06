package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.annotations.ExcludeFromStringConcatenation;
import model.annotations.Tag;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Reference;

import util.serializer.ResourceDeserializer;
import util.serializer.ResourceSerializer;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import conf.Constants;


@Entity
public class Profile extends BaseModel {

  private String firstname;

  private String lastname;

  @Tag
  private List<String> careerLevel = new ArrayList<>();

  @Tag
  private List<String> degrees = new ArrayList<>();

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_FORMAT)
  private Date workExperience;

  private String firstMainFocus;

  private String secondMainFocus;

  @Tag
  private List<String> languages = new ArrayList<>();

  @Tag
  private List<String> industries = new ArrayList<>();

  @Tag
  private List<String> platforms = new ArrayList<>();

  @Tag
  private List<String> opSystems = new ArrayList<>();

  @Tag
  private List<String> progLanguages = new ArrayList<>();

  @Tag
  private List<String> webTechnologies = new ArrayList<>();

  @Tag
  private List<String> devEnvironments = new ArrayList<>();

  @Tag
  private List<String> databases = new ArrayList<>();

  @Tag
  private List<String> certificates = new ArrayList<>();

  @Tag
  private List<String> competences = new ArrayList<>();

  private String summary;

  @Embedded
  @ExcludeFromStringConcatenation
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

  public String getFirstMainFocus() {
    return firstMainFocus;
  }

  public void setFirstMainFocus(String firstMainFocus) {
    this.firstMainFocus = firstMainFocus;
  }

  public String getSecondMainFocus() {
    return secondMainFocus;
  }

  public void setSecondMainFocus(String secondMainFocus) {
    this.secondMainFocus = secondMainFocus;
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

  public List<String> getDatabases() {
    return databases;
  }

  public void setDatabases(List<String> databases) {
    this.databases = databases;
  }

  public List<String> getCertificates() {
    return certificates;
  }

  public void setCertificates(List<String> certificates) {
    this.certificates = certificates;
  }

  public List<String> getCompetences() {
    return competences;
  }

  public void setCompetences(List<String> competences) {
    this.competences = competences;
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

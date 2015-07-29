package model;

import com.fasterxml.jackson.annotation.JsonFormat;

import conf.Constants;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Reference;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.annotations.ExcludeFromStringConcatenation;
import model.annotations.Tag;

@Entity
public class ProjectAssociation extends BaseModel {

  @Reference
  private Project project;

  @Reference
  private Profile profile;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_FORMAT)
  private Date start;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_FORMAT)
  private Date end;

  @Tag
  private List<String> locations = new ArrayList<>();

  @Tag
  @ExcludeFromStringConcatenation
  private List<String> positions = new ArrayList<>();

  private String tasks;

  @Tag
  @ExcludeFromStringConcatenation
  private List<String> technologies = new ArrayList<>();

  public Project getProject() {
    return project;
  }

  public void setProject(Project project) {
    this.project = project;
  }

  public Profile getProfile() {
    return profile;
  }

  public void setProfile(Profile profile) {
    this.profile = profile;
  }

  public Date getStart() {
    return start;
  }

  public void setStart(Date start) {
    this.start = start;
  }

  public Date getEnd() {
    return end;
  }

  public void setEnd(Date end) {
    this.end = end;
  }

  public List<String> getLocations() {
    return locations;
  }

  public void setLocations(List<String> locations) {
    this.locations = locations;
  }

  public List<String> getPositions() {
    return positions;
  }

  public void setPositions(List<String> positions) {
    this.positions = positions;
  }

  public String getTasks() {
    return tasks;
  }

  public void setTasks(String tasks) {
    this.tasks = tasks;
  }

  public List<String> getTechnologies() {
    return technologies;
  }

  public void setTechnologies(List<String> technologies) {
    this.technologies = technologies;
  }
}

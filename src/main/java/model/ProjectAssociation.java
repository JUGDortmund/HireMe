package model;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Reference;

import java.util.Date;
import java.util.List;

import model.annotations.Tag;

@Entity
public class ProjectAssociation extends BaseModel {

  @Reference
  private Project project;

  @Reference
  private Profile profile;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.mm.yy")
  private Date start;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.mm.yy")
  private Date end;
  @Tag
  private List<String> locations;
  @Tag
  private List<String> position;
  private String tasks;
  @Tag
  private List<String> technologies;

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

  public List<String> getPosition() {
    return position;
  }

  public void setPosition(List<String> position) {
    this.position = position;
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

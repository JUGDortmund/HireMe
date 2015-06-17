package model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.annotations.Tag;
import org.mongodb.morphia.annotations.Entity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Project extends BaseModel {

  @Tag
  private List<String> companies = new ArrayList<>();

  @Tag
  private List<String> locations = new ArrayList<>();

  @Tag
  private List<String> industries = new ArrayList<>();

  private String title;
  private String summary;
  private Date start;
  private Date end;

  @JsonCreator
  public static Project create(String jsonString) throws IOException {
    Project project = null;
    ObjectMapper mapper = new ObjectMapper();

    return mapper.readValue(jsonString, Project.class);
  }

  public List<String> getCompanies() {
    return companies;
  }

  public void setCompanies(List<String> companies) {
    this.companies = companies;
  }

  public List<String> getLocations() {
    return locations;
  }

  public void setLocations(List<String> locations) {
    this.locations = locations;
  }

  public List<String> getIndustries() {
    return industries;
  }

  public void setIndustries(List<String> industries) {
    this.industries = industries;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getSummary() {
    return summary;
  }

  public void setSummary(String summary) {
    this.summary = summary;
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
}

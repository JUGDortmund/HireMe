package dtos;

public class BuildProperties {

  private boolean showBuildProperties;
  private String abbrev;
  private String commitUserName;
  private String branch;
  private String commitTime;
  private String buildTime;
  private String environment;

  public boolean isShowBuildProperties() {
    return showBuildProperties;
  }

  public void setShowBuildProperties(boolean showBuildProperties) {
    this.showBuildProperties = showBuildProperties;
  }

  public String getAbbrev() {
    return abbrev;
  }

  public void setAbbrev(String abbrev) {
    this.abbrev = abbrev;
  }

  public String getCommitUserName() {
    return commitUserName;
  }

  public void setCommitUserName(String commitUserName) {
    this.commitUserName = commitUserName;
  }

  public String getBranch() {
    return branch;
  }

  public void setBranch(String branch) {
    this.branch = branch;
  }

  public String getCommitTime() {
    return commitTime;
  }

  public void setCommitTime(String commitTime) {
    this.commitTime = commitTime;
  }

  public String getBuildTime() {
    return buildTime;
  }

  public void setBuildTime(String buildTime) {
    this.buildTime = buildTime;
  }

  public String getEnvironment() {
    return environment;
  }

  public void setEnvironment(String environment) {
    this.environment = environment;
  }
}

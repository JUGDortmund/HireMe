package dtos;

public class GitPropertyDTO {

  private boolean showGitProperties;

  private String abbrev;

  private String commitUserName;

  private String branch;

  private String commitTime;

  private String buildTime;

  public boolean isShowGitProperties() {
    return showGitProperties;
  }

  public void setShowGitProperties(
      boolean showGitProperties) {
    this.showGitProperties = showGitProperties;
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

}

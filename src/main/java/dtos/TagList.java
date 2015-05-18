package dtos;

import java.util.List;

public class TagList {

  private String name;
  private List<String> values;

  private TagList() {
  }

  public TagList(String name, List<String> values) {
    this.name = name;
    this.values = values;
  }

  public String getName() {
    return name;
  }

  public List<String> getValues() {
    return values;
  }
}

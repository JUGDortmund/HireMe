package model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import org.mongodb.morphia.annotations.Entity;

import util.serializer.ByteArrayDeserializer;

import java.util.Date;

@Entity
public class Resource extends BaseModel {

  @JsonDeserialize(using = ByteArrayDeserializer.class)
  private byte[] content;
  private String mimeType;
  private Date lastModified;
  private String name;

  public byte[] getContent() {
    return content;
  }

  public void setContent(byte[] content) {
    this.content = content;
  }

  public String getMimeType() {
    return mimeType;
  }

  public void setMimeType(String mimeType) {
    this.mimeType = mimeType;
  }

  public Date getLastModified() {
    return lastModified;
  }

  public void setLastModified(Date lastModified) {
    this.lastModified = lastModified;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

}

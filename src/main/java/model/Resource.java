package model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Reference;

import util.serializer.ByteArrayDeserializer;
import util.serializer.ResourceDeserializer;
import util.serializer.ResourceSerializer;

import java.util.Date;

@Entity
public class Resource extends BaseModel {

  @JsonDeserialize(using = ByteArrayDeserializer.class)
  private byte[] content;
  private String mimeType;
  private Date lastModified;
  private String name;

  @JsonSerialize(using = ResourceSerializer.class)
  @JsonDeserialize(using = ResourceDeserializer.class)
  @Reference("thumbnail")
  private Resource thumbnail;

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

  public Resource getThumbnail() {
    return thumbnail;
  }

  public void setThumbnail(Resource thumbnail) {
    this.thumbnail = thumbnail;
  }

}

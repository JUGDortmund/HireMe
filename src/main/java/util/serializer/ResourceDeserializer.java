package util.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import org.bson.types.ObjectId;

import java.io.IOException;

import model.Resource;

public class ResourceDeserializer extends JsonDeserializer<Resource> {

  @Override
  public Resource deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
      throws IOException, JsonProcessingException {
    String valueAsString = jsonParser.getValueAsString();
    Resource resource = new Resource();
    resource.setId(new ObjectId(valueAsString));
    return resource;
  }

  @Override
  public Class<Resource> handledType() {
    return Resource.class;
  }
}

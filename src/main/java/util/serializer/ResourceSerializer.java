package util.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

import model.Resource;

public class ResourceSerializer extends JsonSerializer<Resource> {

  @Override
  public void serialize(Resource resource, JsonGenerator jsonGenerator,
      SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
    jsonGenerator.writeString(resource.getId().toString());
  }

  @Override
  public Class<Resource> handledType() {
    return Resource.class;
  }
}

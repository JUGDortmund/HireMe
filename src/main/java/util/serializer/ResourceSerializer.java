package util.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import model.Resource;

import java.io.IOException;

public class ResourceSerializer extends JsonSerializer<Resource> {

  @Override
  public void serialize(Resource resource, JsonGenerator jsonGenerator,
                        SerializerProvider serializerProvider) throws IOException {
    jsonGenerator.writeString(resource.getId().toString());
  }

  @Override
  public Class<Resource> handledType() {
    return Resource.class;
  }
}

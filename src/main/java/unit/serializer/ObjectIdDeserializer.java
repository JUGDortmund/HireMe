package unit.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import org.bson.types.ObjectId;

import java.io.IOException;

public class ObjectIdDeserializer extends JsonDeserializer<ObjectId> {

  @Override
  public ObjectId deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
      throws IOException, JsonProcessingException {
    String valueAsString = jsonParser.getValueAsString();
    return new ObjectId(valueAsString);
  }

  @Override
  public Class<ObjectId> handledType() {
    return ObjectId.class;
  }
}

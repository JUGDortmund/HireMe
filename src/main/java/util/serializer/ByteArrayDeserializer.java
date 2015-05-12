package util.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class ByteArrayDeserializer extends JsonDeserializer<byte[]> {

  @Override
  public byte[] deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
      throws IOException, JsonProcessingException {
    String valueAsString = jsonParser.getValueAsString();
    return valueAsString.getBytes();
  }

  @Override
  public Class<byte[]> handledType() {
    return byte[].class;
  }
}

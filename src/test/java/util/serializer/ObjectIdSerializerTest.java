package util.serializer;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;

public class ObjectIdSerializerTest {

  private ObjectMapper mapper;
  private ObjectId id;

  @Before
  public void setUp() throws Exception {
    mapper = new ObjectMapper();
    SimpleModule module = new SimpleModule();
    module.addSerializer(new ObjectIdSerializer());
    mapper.registerModule(module);
    id = new ObjectId();
  }

  @Test
  public void serialize() throws Exception {
    assertThat(mapper.writeValueAsString(id)).contains(id.toString());
  }

  @Test
  public void handlesTypes() throws Exception {
    assertThat(mapper.canSerialize(ObjectId.class));
  }
}

package unit.util.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.type.TypeFactory;

import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;

import model.Profile;
import unit.serializer.ObjectIdDeserializer;
import unit.serializer.ObjectIdSerializer;

import static org.assertj.core.api.Assertions.assertThat;

public class ObjectIdDeserializerTest {
  private ObjectMapper mapper;
  private ObjectId id;

  @Before
  public void setUp() throws Exception {
    mapper = new ObjectMapper();
    SimpleModule module = new SimpleModule();
    module.addDeserializer(ObjectId.class, new ObjectIdDeserializer());
    module.addSerializer(new ObjectIdSerializer());
    mapper.registerModule(module);
    id = new ObjectId();
  }

  @Test
  public void serialize() throws Exception {
    Profile profile = new Profile();
    profile.setId(id);

    assertThat(mapper.readValue(mapper.writeValueAsString(profile), Profile.class).getId()).isEqualTo(id);
  }

  @Test
  public void handlesTypes() throws Exception {
    assertThat(mapper.canDeserialize(TypeFactory.defaultInstance().constructType(ObjectId.class)));
  }
}
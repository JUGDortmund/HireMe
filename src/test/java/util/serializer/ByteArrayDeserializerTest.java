package util.serializer;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.type.TypeFactory;

import org.junit.Before;
import org.junit.Test;

import model.Profile;

public class ByteArrayDeserializerTest {
  private ObjectMapper mapper;
  private byte[] image;

  @Before
  public void setUp() throws Exception {
    mapper = new ObjectMapper();
    SimpleModule module = new SimpleModule();
    module.addDeserializer(byte[].class, new ByteArrayDeserializer());
    mapper.registerModule(module);
  }

  @Test
  public void deserializeTest() throws Exception {
    Profile profile = new Profile();
    profile.setImage(image);

    assertThat(mapper.readValue(mapper.writeValueAsString(profile), Profile.class).getImage())
        .isEqualTo(image);
  }

  @Test
  public void handlesTypes() throws Exception {
    assertThat(mapper.canDeserialize(TypeFactory.defaultInstance().constructType(byte[].class)));
  }
}

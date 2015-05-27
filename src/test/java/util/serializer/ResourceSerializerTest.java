package util.serializer;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;

import model.Resource;

public class ResourceSerializerTest {

  private ObjectMapper mapper;

  @Before
  public void setUp() throws Exception {
    mapper = new ObjectMapper();
    SimpleModule module = new SimpleModule();
    module.addSerializer(new ResourceSerializer());
    mapper.registerModule(module);
  }

  @Test
  public void serialize() throws Exception {
    Resource resource = new Resource();
    resource.setId(new ObjectId());
    assertThat(mapper.writeValueAsString(resource)).contains(resource.getId().toString());
  }

  @Test
  public void handlesTypes() throws Exception {
    assertThat(mapper.canSerialize(Resource.class));
  }
}

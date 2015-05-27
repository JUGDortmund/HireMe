package util.serializer;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.type.TypeFactory;

import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;

import model.Resource;

public class ResourceDeserializerTest {
  private ObjectMapper mapper;

  @Before
  public void setUp() throws Exception {
    mapper = new ObjectMapper();
    SimpleModule module = new SimpleModule();
    module.addDeserializer(Resource.class, new ResourceDeserializer());
    module.addSerializer(new ResourceSerializer());
    mapper.registerModule(module);
  }

  @Test
  public void deserialize() throws Exception {
    ObjectId id = new ObjectId();
    Resource resource = new Resource();
    resource.setId(id);

    ResourceDeserializerTestClass testClass = new ResourceDeserializerTestClass();
    testClass.setMyField(resource);

    assertThat(
        mapper.readValue(mapper.writeValueAsString(testClass), ResourceDeserializerTestClass.class)
            .getMyField()).isEqualTo(resource);
  }

  @Test
  public void handlesTypes() throws Exception {
    assertThat(mapper.canDeserialize(TypeFactory.defaultInstance().constructType(Resource.class)));
  }

  static class ResourceDeserializerTestClass {
    @JsonSerialize(using = ResourceSerializer.class)
    @JsonDeserialize(using = ResourceDeserializer.class)
    private Resource myField;

    public Resource getMyField() {
      return myField;
    }

    public void setMyField(Resource myField) {
      this.myField = myField;
    }
  }
}

package util.serializer;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.type.TypeFactory;

import org.junit.Before;
import org.junit.Test;

import javax.xml.bind.DatatypeConverter;


public class ByteArrayDeserializerTest {

  private ObjectMapper mapper;
  private byte[] field = DatatypeConverter.parseBase64Binary("abc");

  @Before
  public void setUp() throws Exception {
    mapper = new ObjectMapper();
    SimpleModule module = new SimpleModule();
    module.addDeserializer(byte[].class, new ByteArrayDeserializer());
    mapper.registerModule(module);
  }

  @Test
  public void deserializeTest() throws Exception {
    ByteArraySerializeTestClass testClass = new ByteArraySerializeTestClass();
    testClass.setMyField(field);

    assertThat(
        mapper.readValue(mapper.writeValueAsString(testClass), ByteArraySerializeTestClass.class)
            .getMyField()).isEqualTo(field);
  }

  @Test
  public void handlesTypes() throws Exception {
    assertThat(mapper.canDeserialize(TypeFactory.defaultInstance().constructType(byte[].class)));
  }

  static class ByteArraySerializeTestClass {
    @JsonDeserialize(using = ByteArrayDeserializer.class)
    private byte[] myField;

    public ByteArraySerializeTestClass() {}

    public byte[] getMyField() {
      return myField;
    }

    public void setMyField(byte[] myField) {
      this.myField = myField;
    }
  }

}

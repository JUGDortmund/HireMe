package unit.model;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.bson.types.ObjectId;
import org.junit.Test;

import model.BaseModel;

import static org.assertj.core.api.Assertions.assertThat;

public class BaseModelTest {

  @Test
  public void objectIdSerializerIsUsed() throws Exception {
    BaseModel model= new BaseModel();
    ObjectId id = new ObjectId();
    model.setId(id);
    ObjectMapper mapper = new ObjectMapper();
    assertThat(mapper.writeValueAsString(model)).contains(id.toString());
  }

  @Test
  public void objectIdDeserializerIsUsed() throws Exception {
    BaseModel model= new BaseModel();
    ObjectId id = new ObjectId();
    model.setId(id);
    ObjectMapper mapper = new ObjectMapper();
    assertThat(mapper.readValue(mapper.writeValueAsString(model), BaseModel.class)).isEqualTo(model);
  }
}

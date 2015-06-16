package unit.util;

import model.BaseModel;
import model.Profile;
import org.junit.Test;
import util.ReflectionUtil;

import javax.validation.constraints.NotNull;
import java.lang.reflect.Field;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Lukas Eichler
 */
public class ReflectionUtilTest {

  @Test
  public void isStringList() throws Exception {
    assertThat(ReflectionUtil.isStringList(getField("stringList"))).isTrue();
    assertThat(ReflectionUtil.isStringList(getField("anotherList"))).isFalse();
    assertThat(ReflectionUtil.isStringList(getField("notAList"))).isFalse();
  }
  
  @Test
  public void isModelList() throws Exception {
    assertThat(ReflectionUtil.isModelList(getField("modelList"))).isTrue();
    assertThat(ReflectionUtil.isModelList(getField("anotherModelList"))).isTrue();
    assertThat(ReflectionUtil.isModelList(getField("anotherList"))).isFalse();
    assertThat(ReflectionUtil.isModelList(getField("notAList"))).isFalse();
  }
  
  private Field getField(final @NotNull String fieldName) throws NoSuchFieldException {
    return TestClass.class.getDeclaredField(fieldName);
  }

  private static class TestClass {
    List<String> stringList;
    List<BaseModel> modelList;
    List<Profile> anotherModelList;
    List<Integer> anotherList;
    boolean notAList;
  }
}
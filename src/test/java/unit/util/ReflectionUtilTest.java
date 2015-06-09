package unit.util;

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

  private static class TestClass {
    List<String> stringList;
    List<Integer> anotherList;
    boolean notAList;
  }

  @Test
  public void successfullyFindStringList() throws Exception {
    assertThat(ReflectionUtil.isStringList(getField("stringList"))).isTrue();
  }
  
  @Test
  public void doNotFindOtherTypesOfList() throws Exception {
    assertThat(ReflectionUtil.isStringList(getField("anotherList"))).isFalse();
  }
  
  @Test
  public void doNotFindOtherFieldTypes() throws Exception {
    assertThat(ReflectionUtil.isStringList(getField("notAList"))).isFalse();
  }
  
  private static class TestClass {
    List<String> stringList;
    List<Integer> anotherList;
    boolean notAList;
  }

  private Field getField(final @NotNull String fieldName) throws NoSuchFieldException {
    return TestClass.class.getDeclaredField(fieldName);
  }
}
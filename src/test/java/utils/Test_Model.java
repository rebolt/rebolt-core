package utils;

import io.rebolt.core.models.Model;
import io.rebolt.core.utils.HashUtil;
import io.rebolt.core.utils.StringUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public final class Test_Model {

  @AllArgsConstructor
  public static class ModelImpl extends Model<ModelImpl> {
    final @Getter String value;

    @Override
    public boolean isEmpty() {
      return StringUtil.isNullOrEmpty(value);
    }

    @Override
    public long deepHash() {
      return HashUtil.deepHash(value);
    }
  }

  @Test
  public void test_model() {
    ModelImpl model1 = new ModelImpl("12345");
    ModelImpl model2 = new ModelImpl("12345");
    ModelImpl model3 = new ModelImpl("123456");

    assertTrue(model1.equals(model2));
    assertFalse(model1.equals(model3));
  }
}

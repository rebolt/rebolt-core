package utils;

import io.rebolt.core.utils.NetworkUtil;
import io.rebolt.core.utils.ObjectUtil;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public final class Test_NetworkUtil {

  @Test
  public void test_getHost() {
    String host = NetworkUtil.getHost();
    assertTrue(!ObjectUtil.isEmpty(host));
  }
}

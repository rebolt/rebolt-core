package utils;

import io.rebolt.core.utils.UriUtil;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

public class Test_UriUtil {

  @Test
  public void test_containsHost() {
    final String host1 = "https://www.nexon.com";
    final String host2 = "https://nexon.com/forum/123";

    assertTrue(UriUtil.containsHost(host1, host2));

    final String host3 = "http://NEXON.com/123";

    assertTrue(UriUtil.containsHost(host1, host3));
  }
}

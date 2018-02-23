package utils;

import io.rebolt.core.utils.UriUtil;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class Test_UriUtil {

  @Test
  public void test_containsHost() {
    final String host1 = "https://www.nexon.com";
    final String host2 = "https://nexon.com/forum/123";

    assertTrue(UriUtil.containsHost(host1, host2));

    final String host3 = "http://NEXON.com/123";

    assertTrue(UriUtil.containsHost(host1, host3));

    final String host4 = "unknown://///NEXON.com/123";

    assertFalse(UriUtil.containsHost(host3, host4));
  }

  @Test
  public void test_getHost() {
    final String host1 = "https://www.nexon.com";
    final String host2 = "nexon.com";

    assertEquals(UriUtil.getHost(host1), host2);
    assertEquals(UriUtil.getHost(null), null);
    assertEquals(UriUtil.getHost(""), null);
  }

  @Test
  public void test_getOrigin() {
    final String host1 = "https://www.nexon.com/";
    final String host2 = "https://nexon.com/forum/123";
    final String host3 = "http://nexon.com:8444/forum";

    assertEquals(UriUtil.getOrigin(host1), "https://www.nexon.com");
    assertEquals(UriUtil.getOrigin(host2), "https://nexon.com");
    assertEquals(UriUtil.getOrigin(host3), "http://nexon.com:8444");
  }
}


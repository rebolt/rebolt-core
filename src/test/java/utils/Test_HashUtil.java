package utils;

import com.google.common.collect.Sets;
import io.rebolt.core.utils.StringUtil;
import org.junit.Test;

import java.util.Set;

import static io.rebolt.core.utils.HashUtil.djb2Hash;
import static org.junit.Assert.assertTrue;

public final class Test_HashUtil {

  @Test
  public void test_djb2Hash() {
    final int loopCount = 100000;
    final Set<Long> resultContainers = Sets.newHashSetWithExpectedSize(loopCount);
    for (int i = 0; i < loopCount; i++) {
      resultContainers.add(djb2Hash(StringUtil.randomAlpha(512)));
    }
    // assert
    assertTrue(resultContainers.size() == loopCount);
  }

}

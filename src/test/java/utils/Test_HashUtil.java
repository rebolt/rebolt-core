package utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import io.rebolt.core.models.AbstractModel;
import io.rebolt.core.utils.HashUtil;
import io.rebolt.core.utils.StringUtil;
import org.junit.Test;

import java.util.List;
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

  @Test
  public void test_deepHash() {
    final int loopCount = 100000;

    // test 1
    int index = 0;
    List<Object[]> argumentList = Lists.newLinkedList();
    for (int i = 0; i < loopCount; i++) {
      argumentList.add(new Object[] {index++, Lists.newArrayList('A', 'B', 'C'), true});
    }
    Set<Long> hashedList = Sets.newHashSet();
    argumentList.forEach(entry -> hashedList.add(HashUtil.deepHash(entry)));
    assertTrue(hashedList.size() == loopCount);

    // test 2
    index = 0;
    argumentList.clear();
    for (int i = 0; i < loopCount; i++) {
      argumentList.add(new Object[] {Lists.newArrayList('A', 'B', 'C'), index++, true});
    }
    hashedList.clear();
    argumentList.forEach(entry -> hashedList.add(HashUtil.deepHash(entry)));
    assertTrue(hashedList.size() == loopCount);

    // test 3
    index = 0;
    argumentList.clear();
    for (int i = 0; i < loopCount; i++) {
      argumentList.add(new Object[] {Lists.newArrayList('A', 'B', 'C'), true, index++});
    }
    hashedList.clear();
    argumentList.forEach(entry -> hashedList.add(HashUtil.deepHash(entry)));
    assertTrue(hashedList.size() == loopCount);

  }

  @Test
  public void test_uniqueDeepHash() {
    final int loopCount = 100000;

    // test 1
    int index = 0;
    List<Object[]> argumentList = Lists.newLinkedList();
    for (int i = 0; i < loopCount; i++) {
      argumentList.add(new Object[] {index++, new char[] {'a', 'b', 'c'}, true});
    }
    Set<Long> hashedList = Sets.newHashSet();
    argumentList.forEach(entry -> hashedList.add(HashUtil.uniqueDeepHash(entry)));
    assertTrue(hashedList.size() == loopCount);
  }

  @Test
  public void test_modelDeepHash() {
    final int loopCount = 100000;

    List<DeepModel[]> arguments = Lists.newLinkedList();
    for (int i = 0; i < loopCount; i++) {
      arguments.add(new DeepModel[] {
        new DeepModel(), new DeepModel(), new DeepModel()
      });
    }
    Set<Long> hashedList = Sets.newTreeSet();
    arguments.forEach(entry -> hashedList.add(HashUtil.modelDeepHash(entry)));

    assertTrue(hashedList.size() == loopCount);
  }

  private static class DeepModel extends AbstractModel {

    private final int hashCode;

    public DeepModel() {
      this.hashCode = (int) HashUtil.deepHash(StringUtil.randomAlpha(1024));
    }

    @Override
    public boolean isEmpty() {
      return false;
    }

    @Override
    public int hashCode() {
      return hashCode;
    }
  }

}

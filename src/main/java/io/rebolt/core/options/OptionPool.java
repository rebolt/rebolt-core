package io.rebolt.core.options;

import com.google.common.collect.Maps;
import io.rebolt.core.exceptions.NotInitializedException;
import io.rebolt.core.utils.ClassUtil;

import java.util.Map;

/**
 * 프로젝트내에서 사용되는 모든 옵션들을 관리한다
 */
public final class OptionPool {

  /**
   * 이 컨테이너에는 프로젝트에서 사용하는 모든 옵션명, 옵션키, 옵션값이 저장되어 있다.
   */
  private final static Map<String, Map<String, String>> optionMap = Maps.newHashMap();

  public static OptionPool getInstance() {
    return ClassUtil.getSingleton(OptionPool.class);
  }

  /**
   * 옵션을 추가한다
   *
   * @param group 옵션그룹
   * @param key   옵션키
   * @param value 옵션값
   */
  public synchronized void addOption(String group, String key, String value) {
    Map<String, String> optionGroup = optionMap.get(group);
    if (optionGroup == null) {
      optionMap.put(group, Maps.newHashMap());
      optionGroup = optionMap.get(group);
    }
    optionGroup.put(key, value);
  }

  /**
   * 옵션ㅇ르 조회한다
   *
   * @param group 옵션그룹, 만약 옵션그룹이 존재하지 않는다면 {@link NotInitializedException} 발생
   * @param key   옵션키
   * @return 옵션값
   */
  public String getOption(String group, String key) {
    if (!optionMap.containsKey(group)) {
      throw new NotInitializedException("Empty option group: " + group);
    }
    return optionMap.get(group).get(key);
  }

}

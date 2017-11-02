package io.rebolt.core.options;

import io.rebolt.core.utils.StringUtil;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 프로젝트에서 사용하는 JVM Option들을 직접 정의하고, 빠르게 사용할 수 있도록 해준다.
 * {@link Option} 클래스를 상속받아 옵션그룹(Option group)과 옵션키(Option Key)들을 정의할 수 있다.
 * <p>
 * Option name : 사용할 옵션키들은 옵션그룹으로 그룹핑된다.
 * Option key : 실제로 사용할 옵션키를 정의한다. 정의된 옵션키는 JVM Option과 호환된다.
 */
public abstract class Option implements Serializable {

  private final OptionPool optionPool;
  private final String group;

  protected Option() {
    this.optionPool = OptionPool.getInstance();
    this.group = setGroup();
    Map<String, String> options = new HashMap<>();
    setOptions(options);
    options.forEach((key, value) -> optionPool.addOption(group, key, value));
  }

  /**
   * 사용할 옵션그룹명을 정의한다.
   * 기존에 사용한 옵션그룹명과 겹치지 않도록 주의한다.
   *
   * @return 옵션명 (Option name)
   */
  public abstract String setGroup();

  /**
   * 사용할 옵션키, 옵션값을 정의한다.
   * 여기서 정의된 옵션키는 JVM Option과 호환된다.
   *
   * @param options put(옵션키, 옵션값)
   */
  public abstract void setOptions(Map<String, String> options);

  /**
   * 옵션 조회
   *
   * @param key 옵션키
   * @param type 옵션값 클래스타입
   * @param <T> 제네릭
   * @return 옵션값
   */
  protected <T> T get(String key, Class<T> type) {
    String value = optionPool.getOption(group, key);
    if (StringUtil.isNullOrEmpty(value)) {
      return null;
    }
    return StringUtil.cast(value, type);
  }

  /**
   * 옵션값 변경
   * 주의: 라이브콜에 연동시키지 않도록 한다.
   *
   * @param key 옵션키
   * @param value 옵션값
   */
  protected void set(String key, String value) {
    optionPool.addOption(group, key, value);
  }
}

package io.rebolt.core.options;

import io.rebolt.core.exceptions.ReboltException;
import io.rebolt.core.utils.ClassUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DefaultOption extends Option {

  private static final long serialVersionUID = -108190947243609652L;
  private static final String KEY_EXCEPTION_LOG = "rebolt.log.exception";

  public static DefaultOption getInstance() {
    return ClassUtil.getSingleton(DefaultOption.class);
  }

  @Override
  public String setGroup() {
    return "default";
  }

  @Override
  public void setOptions(Map<String, String> options) {
    options.put(KEY_EXCEPTION_LOG, String.valueOf(false));
  }

  /**
   * {@link ReboltException} 발생시 자동로그 활성화 여부
   *
   * @return true : 자동로그 활성화, false : 자동로그 비활성화
   */
  public boolean isExceptionLog() {
    return get(KEY_EXCEPTION_LOG, Boolean.class);
  }

}

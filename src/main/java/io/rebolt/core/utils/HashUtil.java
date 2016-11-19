package io.rebolt.core.utils;

import io.rebolt.core.exceptions.IllegalParameterException;
import io.rebolt.core.models.AbstractModel;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

/**
 * 자바 오브젝트를 활용해 해시코드를 생성한다
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class HashUtil {
  private static final long NO_PARAM_KEY = Long.MIN_VALUE;
  private static final long NULL_PARAM_KEY = 53L;

  /**
   * DJB2 해시코드
   *
   * @param value 변환할 {@link String}
   * @return Djb2 해시코드 (음수포함)
   */
  public static long djb2Hash(String value) {
    long hash = 5381L;
    if (!StringUtil.isNullOrEmpty(value)) {
      for (char entity : value.trim().toCharArray()) {
        hash = ((hash << 5) + hash) + entity;
      }
    }
    return hash;
  }

  /**
   * Deep 해시코드
   * <p>
   * Iterable, Map도 가능하나 매번 다른 해시코드가 반환될 수 있다.
   * <p>
   * 주의 : 해시알고리즘을 절대로 변경해선 안됨.
   * 개선된 해시알고리즘을 사용하고자 한다면 deepHash2와 같은 새로운 메소드를 작성할 것은 권고.
   *
   * @param objects 해시 대상 인스턴스 배열
   * @return 해시코드 (음수포함)
   * @since 0.1.0
   */
  public static long deepHash(Object... objects) {
    Objects.requireNonNull(objects);
    if (objects.length == 0) {
      return NO_PARAM_KEY;
    }
    long hashCode = 17L;
    for (Object object : objects) {
      if (object instanceof Iterable) {
        long sum = 0;
        for (Object entry : (Iterable) object) {
          sum ^= entry.hashCode();
        }
        hashCode = 31L * hashCode + sum;
      } else if (object instanceof Map) {
        long sum = 0;
        for (Object entry : ((Map) object).values()) {
          sum ^= entry.hashCode();
        }
        hashCode = 31L * hashCode + sum;
      } else if (object instanceof Object[]) {
        hashCode = 31L * hashCode + Arrays.deepHashCode((Object[]) object);
      } else {
        hashCode = 31L * hashCode + (object == null ? NULL_PARAM_KEY : object.hashCode());
      }
    }
    return hashCode;
  }

  /**
   * Deep Unique 해시코드
   * <p>
   * Iterable, Map등의 Generic 클래스는 파라미터로 사용될 수 없다.
   * 만약 파라미터로 사용된다면 {@link IllegalParameterException}가 발생한다.
   * <p>
   * 주의 : 해시알고리즘을 절대로 변경해선 안됨.
   * 개선된 해시알고리즘을 사용하고자 한다면 deepUniqueHash2와 같은 새로운 메소드를 작성할 것은 권고.
   *
   * @param objects 해시 대상 인스턴스 배열
   * @return 해시코드 (음수포함)
   * @since 0.1.0
   */
  public static long uniqueDeepHash(Object... objects) {
    Objects.requireNonNull(objects);
    if (objects.length == 0) {
      return NO_PARAM_KEY;
    }
    long hashCode = 17L;
    for (Object object : objects) {
      if (object instanceof Iterable || object instanceof Map || object instanceof Iterator) {
        throw new IllegalParameterException("not supported type: " + object.getClass());
      } else if (object instanceof Object[]) {
        hashCode = 31L * hashCode + Arrays.deepHashCode((Object[]) object);
      } else {
        hashCode = 31L * hashCode + (object == null ? NULL_PARAM_KEY : object.hashCode());
      }
    }
    return hashCode;
  }

  /**
   * Deep 해시코드
   * <p>
   * {@link AbstractModel}을 상속받은 클래스만을 이용해서 hashCode를 생성한다.
   * 이러한 방식은 인스턴스에서 반드시 유일한 hashCode를 생성해야 하는 엄격한 규칙을
   * 정해야 하는 경우에만 사용하도록 한다.
   * (예) 캐시 데이터의 구분키, 데이터 샤딩키등
   *
   * @param models {@link AbstractModel}을 상속받은 인스턴스 배열
   * @return 해시코드
   * @since 0.1.0
   */
  @SuppressWarnings("unchecked")
  public static <T extends AbstractModel> long modelDeepHash(T... models) {
    Objects.requireNonNull(models);
    if (models.length == 0) {
      return NO_PARAM_KEY;
    }
    long hashCode = 17L;
    for (AbstractModel model : models) {
      hashCode = 31L * hashCode + model.hashCode();
    }
    return hashCode;
  }

}

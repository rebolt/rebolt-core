package io.rebolt.core.utils;

import io.rebolt.core.models.IModel;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

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
   * <p>
   * String.hashCode() 보다는 성능이 느리다.
   *
   * @param value 변환할 {@link String}
   * @return Djb2 해시코드 (음수포함)
   * @since 0.1.0
   */
  public static long djb2Hash(String value) {
    long hash = 5381L;
    if (!ObjectUtil.isNull(value)) {
      for (int i = 0; i < value.length(); i++) {
        hash = ((hash << 5) + hash) + value.charAt(i);
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
    if (objects == null || objects.length == 0) {
      return NO_PARAM_KEY;
    }
    long hashCode = 17L;
    for (Object object : objects) {
      if (object == null) {
        hashCode = 31L * hashCode + NULL_PARAM_KEY;
        continue;
      }
      long sum = 0;
      switch (object.getClass().getSimpleName()) {
        case "Integer":
          hashCode = 31L * hashCode + (Integer) object;
          break;
        case "Long":
          hashCode = 31L * hashCode + (Long) object;
          break;
        case "Double":
          hashCode = 31L * hashCode + ((Double) object).longValue();
          break;
        case "Float":
          hashCode = 31L * hashCode + ((Float) object).longValue();
          break;
        case "Short":
          hashCode = 31L * hashCode + (Short) object;
          break;
        case "Byte":
          hashCode = 31L * hashCode + (Byte) object;
          break;
        case "Character":
          hashCode = 31L * hashCode + (Character) object;
          break;
        case "int[]":
          for (int entry : (int[]) object) {
            sum ^= entry;
          }
          hashCode = 31L * hashCode + sum;
          break;
        case "long[]":
          for (long entry : (long[]) object) {
            sum ^= entry;
          }
          hashCode = 31L * hashCode + sum;
          break;
        case "double[]":
          for (double entry : (double[]) object) {
            sum ^= (long) entry;
          }
          hashCode = 31L * hashCode + sum;
          break;
        case "float[]":
          for (float entry : (float[]) object) {
            sum ^= (long) entry;
          }
          hashCode = 31L * hashCode + sum;
          break;
        case "short[]":
          for (short entry : (short[]) object) {
            sum ^= (int) entry;
          }
          hashCode = 31L * hashCode + sum;
          break;
        case "byte[]":
          for (byte entry : (byte[]) object) {
            sum ^= entry;
          }
          hashCode = 31L * hashCode + sum;
          break;
        case "char[]":
          for (char entry : (char[]) object) {
            sum ^= entry;
          }
          hashCode = 31L * hashCode + sum;
          break;
        default:
          if (object instanceof Iterable) {
            sum = 0;
            for (Object entry : (Iterable) object) {
              sum ^= entry.hashCode();
            }
            hashCode = 31L * hashCode + sum;
          } else if (object instanceof Map) {
            sum = 0;
            for (Object entry : ((Map) object).values()) {
              sum ^= entry.hashCode();
            }
            hashCode = 31L * hashCode + sum;
          } else if (object instanceof Object[]) {
            sum = 0;
            for (Object entry : (Object[]) object) {
              sum ^= entry.hashCode();
            }
            hashCode = 31L * hashCode + sum;
          } else if (object instanceof IModel) {
            hashCode = 31L * hashCode + ((IModel) object).deepHash();
          } else {
            hashCode = 31L * hashCode + object.hashCode();
          }
          break;
      }
    }
    return hashCode;
  }

  /**
   * Deep 해시코드
   * <p>
   * {@link IModel}을 상속받은 클래스만을 이용해서 hashCode를 생성한다.
   * 이러한 방식은 인스턴스에서 반드시 유일한 hashCode를 생성해야 하는 엄격한 규칙을
   * 정해야 하는 경우에만 사용하도록 한다.
   * (예) 캐시 데이터의 구분키, 데이터 샤딩키등
   *
   * @param models {@link IModel}을 상속받은 인스턴스 배열
   * @return 해시코드
   * @since 0.1.0
   */
  @SuppressWarnings("unchecked")
  public static <T extends IModel> long modelDeepHash(T... models) {
    Objects.requireNonNull(models);
    if (models.length == 0) {
      return NO_PARAM_KEY;
    }
    long hashCode = 17L;
    for (IModel model : models) {
      hashCode = 31L * hashCode + model.deepHash();
    }
    return hashCode;
  }

}

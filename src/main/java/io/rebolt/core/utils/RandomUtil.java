package io.rebolt.core.utils;

import java.util.SplittableRandom;

/**
 * @since 0.2.26
 */
public final class RandomUtil {
  private static final char[] alphaDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
      'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
      'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

  /**
   * A generator of uniform pseudorandom values applicable for use in (among other contexts) isolated parallel computations that may generate subtasks. Class SplittableRandom
   * supports methods for producing pseudorandom numbers of type int, long, and double with similar usages as for class Random but differs in the following ways:
   * <p>
   * Series of generated values pass the DieHarder suite testing independence and uniformity properties of random number generators. (Most recently validated with version 3.31.1.)
   * These tests validate only the methods for certain types and ranges, but similar properties are expected to hold, at least approximately, for others as well. The period (length
   * of any series of generated values before it repeats) is at least 264.
   * <p>
   * Method split() constructs and returns a new SplittableRandom instance that shares no mutable state with the current instance. However, with very high probability, the values
   * collectively generated by the two objects have the same statistical properties as if the same quantity of values were generated by a single thread using a single
   * SplittableRandom object.
   * <p>
   * Instances of SplittableRandom are not thread-safe. They are designed to be split, not shared, across threads. For example, a fork/join-style computation using random numbers
   * might include a construction of the form new Subtask(aSplittableRandom.split()).fork().
   * <p>
   * This class provides additional methods for generating random streams, that employ the above techniques when used in stream.parallel() mode. Instances of SplittableRandom are
   * not cryptographically secure. Consider instead using SecureRandom in security-sensitive applications. Additionally, default-constructed instances do not use a
   * cryptographically random seed unless the system property java.util.secureRandomSeed is set to true.
   * <p>
   * Since: 1.8
   */
  private final static SplittableRandom random = new SplittableRandom();

  /**
   * 고정된 길이의 랜덤문자열 생성. 알파벳과 숫자의 조합.
   *
   * @param length 길이
   * @return 고정된 길이의 랜덤 문자열
   * @since 0.1.0
   */
  public static String randomAlpha(int length) {
    if (length <= 0) {
      throw new IllegalArgumentException("A random string's length cannot be zero or negative");
    }
    char[] buffer = new char[length];
    for (int i = 0; i < length; i++) {
      buffer[i] = alphaDigits[random.nextInt(alphaDigits.length)];
    }
    return new String(buffer);
  }

  /**
   * 가변 길이의 랜덤문자열 생성. 알파벳과 숫자의 조합.
   *
   * @param min 최소길이
   * @param max 최대길이
   * @return 가변 길이의 랜덤 문자열
   * @since 0.1.0
   */
  public static String randomAlpha(int min, int max) {
    if (min <= 0 || max <= 0 || min > max) {
      throw new IllegalArgumentException("A random string's length cannot be zero or negative or min > max");
    }
    return randomAlpha(random.nextInt(max - min) + min);
  }

  /**
   * 랜덤 long
   *
   * @return 랜덤 long
   * @since 0.2.7
   */
  public static long randomLong() {
    return random.nextLong();
  }

  /**
   * 랜덤 long
   *
   * @param max 최대수
   * @return 최대수를 넘지 않는 랜덤 long
   * @since 0.2.7
   */
  public static long randomLong(long max) {
    return random.nextLong(++max);
  }

  /**
   * 랜덤 long
   *
   * @param min 최소수
   * @param max 최대수
   * @return 최소 ~ 최대사이의 랜덤 long
   * @since 0.2.7
   */
  public static long randomLong(long min, long max) {
    return random.nextLong(min, ++max);
  }

  /**
   * 랜덤 int
   *
   * @return 랜덤 int
   * @since 0.2.7
   */
  public static int randomInt() {
    return random.nextInt();
  }

  /**
   * 랜덤 int
   *
   * @param max 최대수
   * @return 최대수를 넘지 않는 랜덤 int
   * @since 0.2.7
   */
  public static int randomInt(int max) {
    return random.nextInt(++max);
  }

  /**
   * 랜덤 int
   *
   * @param min 최소수
   * @param max 최대수
   * @return 최소 ~ 최대사이의 랜덤 int
   * @since 0.2.7
   */
  public static int randomInt(int min, int max) {
    return random.nextInt(min, ++max);
  }

  /**
   * 랜덤 boolean
   *
   * @return true or false
   * @since 0.2.26
   */
  public static boolean randomBoolean() {
    return random.nextBoolean();
  }

}
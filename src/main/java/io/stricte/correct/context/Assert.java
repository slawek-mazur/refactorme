package io.stricte.correct.context;

import java.util.Collection;

public class Assert {

  public static void hasText(String text, String message) {
    if (null == text || text.trim().isEmpty()) {
      throw new IllegalArgumentException(message);
    }
  }

  public static void notEmpty(Collection<?> collection, String message) {
    if (null == collection || collection.isEmpty()) {
      throw new IllegalArgumentException(message);
    }
  }

  public static void isTrue(boolean expression, String message) {
    if (!expression) {
      throw new IllegalArgumentException(message);
    }
  }

  public static void notNull(Object object, String message) {
    if (object == null) {
      throw new IllegalArgumentException(message);
    }
  }
}

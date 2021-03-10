package com.agility.marketservice.util;

import java.util.Arrays;

/**
 * Created by Vuong Nguyen
 */
public class ArrayUtil {
  /**
   * Checking array String contain a value
   *
   * @param value
   * @param arr
   * @return
   */
  public static boolean isContainValue(String value, String[] arr) {
    if (arr == null || arr.length == 0) return false;

    return Arrays.stream(arr).anyMatch(s -> s.equalsIgnoreCase(value));
  }
}

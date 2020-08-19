package com.agility.chapter5;

public class ExampleString {
  public static void testCompareString() {
    String s2 = "abc";
    System.out.println("abc".contentEquals(s2));
    System.out.println("abc".equals(s2));

    String newStr1 = String.join(",", "abc", "xyz", "cdg", "ddd");
    System.out.println(newStr1);
  }
}

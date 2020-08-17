package com.agility;

public interface IProduct {
  int test = 12;
  String get();
  void add();
  default int getQuantity(){
    return 10 + test;
  }
  static String someMethod() {
    return "abc";
  }
}

package com.agility.exception;

import java.io.IOException;

public class ExampleBasic implements INumber {

  @Override
  public boolean isNegativeNumber(int number) {
    if (number < 0) {
      return true;
    }
    return false;
  }

  @Override
  public float divideTwoNumber(int number1, int number2) throws Exception {
    if (number2 == 0) {
      throw new RuntimeException("The number2 invalid.");
    }
    return (float) number1 / number2;
  }

  public void aMethod(boolean isCheck) throws IOException {
    if (!isCheck) {
      throw new IOException("Device error");
    }
  }
}

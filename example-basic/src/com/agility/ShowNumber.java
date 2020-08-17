package com.agility;

public class ShowNumber {
  private static int numberOne = 5;
  private static int numberTwo = 10;

  public static  void showNumber() {
    System.out.println("Total:  " + (numberOne + numberTwo));
    numberOne += numberOne;
    numberTwo += numberTwo;
  }
}

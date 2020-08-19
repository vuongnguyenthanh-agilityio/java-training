package com.agility;

import com.agility.chapter5.FileExample;
import com.agility.exception.ExampleBasic;

import java.io.IOException;

public class Main {

  public static void main(String[] args) {
//    Example for exception
    ExampleBasic exam = new ExampleBasic();
    boolean test = exam.isNegativeNumber(10);
    try {
      float result2 = exam.divideTwoNumber(12, 0);
      exam.aMethod(false);
    } catch (RuntimeException ex) {
      System.out.println("Error: "+ ex.getMessage());
      ex.printStackTrace();
    } catch (IOException ex) {
      ex.printStackTrace();
    } catch (Exception ex) {
      ex.printStackTrace();
    }

    // Example for String
//    ExampleString.testCompareString();

    // Example File manager
    FileExample fileExample = new FileExample("sources/");
    fileExample.createFile("hello.txt");
    fileExample.writeFile("hello.txt", "Write some text in a file");
    String textResult = fileExample.readFile("hello.txt");
    System.out.println("textResult: " + textResult);
  }


}

package com.agility;

import com.agility.chapter5.FileExample;
import com.agility.chapter6.CollectionExample;
import com.agility.exception.ExampleBasic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {
  /**
   * Example for exception
   */
  private static void testException() {
    ExampleBasic exam = new ExampleBasic();
    boolean test = exam.isNegativeNumber(10);
    try {
      float result2 = exam.divideTwoNumber(12, 0);
      exam.aMethod(false);
    } catch (RuntimeException ex) {
      System.out.println("Error: " + ex.getMessage());
      ex.printStackTrace();
    } catch (IOException ex) {
      ex.printStackTrace();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  /**
   * Example File manager
   */
  private static void testFileManager() {
    FileExample fileExample = new FileExample("sources/");
    fileExample.createFile("hello.txt");
    fileExample.writeFile("hello.txt", "Write some text in a file");
    String textResult = fileExample.readFile("hello.txt");
    System.out.println("textResult: " + textResult);
  }

  private static void testCollection() {
    CollectionExample collectionExample = new CollectionExample();
//    collectionExample.testInitialize();
//    collectionExample.testMethod();
//    collectionExample.testCompare();
    collectionExample.testCollectionClass();
  }

  public static void main(String[] args) {
//    testException();
//    testFileManager();
    testCollection();
  }


}

package com.agility;

import com.agility.chapter5.FileExample;
import com.agility.chapter6.CollectionExample;
import com.agility.exception.ExampleBasic;

import java.io.IOException;

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
    collectionExample.testMethod();
  }

  private static void testCompareObject() {
    Product product1 = new Product("Test1", "1");
    Product product2 = new Product("Test1", "1");
    System.out.println("hashCode 1: "+ product1.hashCode());
    System.out.println("hashCode 2: "+ product2.hashCode());
    System.out.println("Equals: "+ product2.equals(product1));
  }

  public static void main(String[] args) {
//    testException();
//    testFileManager();
//    testCollection();
    testCompareObject();
  }


}

package com.agility.chapter5;

import java.io.*;

public class FileExample {
  private String path;

  public FileExample(String path) {
    this.path = path;
  }

  /**
   *
   * @param fileName
   * @return
   */
  public boolean createFile(String fileName) {
    File file = new File(this.path, fileName);
    boolean success = false;
    try {
      success = file.createNewFile();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return success;
  }

  /**
   *
   * @param fileName
   * @return
   */
  public String readFile(String fileName) {
    String filePath = this.path + File.separator + fileName;
    String textResult = "";
    try {
      InputStream fileStream = new FileInputStream(filePath);
      int data;
      while ((data = fileStream.read()) != -1) {
        textResult += (char) data;
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return textResult;
  }

  /**
   *
   * @param fileName
   * @param text
   */
  public void writeFile(String fileName, String text) {
    String filePath = this.path + File.separator + fileName;
    try {
      OutputStream outputStream = new FileOutputStream(filePath);
      PrintStream printStream = new PrintStream(outputStream);
      printStream.println(text);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }
}

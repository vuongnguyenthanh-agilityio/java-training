package chapter13.product;

public class ProductException extends RuntimeException{

  public ProductException(String message, Throwable error) {
    super(message, error);
  }
}

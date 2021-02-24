package chapter13;

import chapter13.product.Product;
import chapter13.product.ProductValidation;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;

public class FunctionalProgramming {
  public static void main(String[] args) {
    System.out.println("Test run Maven: " + args[1]);
//    FunctionalProgramming fn = new FunctionalProgramming();
//    fn.testConsumer();
    Product product = new Product("Iphone 6", "", -10);
    ProductValidation productValidation = new ProductValidation();
    productValidation.validate(product);
  }

  private void testConsumer() {
    List<String> tasks = new ArrayList<>();
    tasks.add("React");
    tasks.add("NodeJS");
    tasks.add("NestJS");
    tasks.add("Java");

    Consumer<String> printConsumer = s -> {
      System.out.println(s);
    };

    Consumer<List<String>> upperCaseConsumer = list -> {
      for (int i = 0, size = tasks.size(); i < size; i++) {
        list.set(i, list.get(i).toUpperCase());
      }
    };

    Consumer<List<String>> printUpperCaseConsumer = list -> {
      list.forEach(printConsumer);
    };

    upperCaseConsumer.andThen(printUpperCaseConsumer).accept(tasks);
  }
}

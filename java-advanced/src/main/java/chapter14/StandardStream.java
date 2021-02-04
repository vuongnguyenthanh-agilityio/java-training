package chapter14;

import chapter13.product.Product;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StandardStream {
  public static void main(String [] args) {
    System.out.println("Test Stream: ");
    StandardStream standardStream = new StandardStream();
    standardStream.testProduct();
  }

  private void testOperation() {
    List<Integer> list = List.of(1,2,15,4);
    Stream<Integer> newList = list.stream().skip(2);
    newList.forEach(System.out::print);
    list.stream().forEach(System.out::print);
    list.forEach(n -> {
      System.out.println("N: "+ n);
      Stream<Integer> stream = Stream.iterate(0, i -> i < n, i -> i +2);
      stream.forEach(System.out::println);
    });
    System.out.println("LIST2: "+ list.toString());
    Function<Integer, Stream<Integer>> platMap = n -> Stream.iterate(1, i -> i < n, i -> i + 5);
    Stream<Integer> list2 = list.stream().flatMap(n -> Stream.iterate(0, i -> i < n, i -> i + 2));
//    list2.forEach(System.out::println);

    Stream.concat(Set.of(42).stream(),
        List.of(42).stream()).limit(1)
        .forEach(System.out::print);
  }

  private void testProduct() {
    List<Product> products = List.of(
        new Product("Iphone 5", "A", 12),
        new Product("Iphone 6", "B", 16),
        new Product("Iphone 7", "C", 20),
        new Product("Iphone 8", "D", 25),
        new Product("SamSum S15", "F", 10)
    );

    System.out.println("SORT: ---");
    // Sort products
    Stream<Product> sortProducts =  products.stream().sorted((p1, p2) -> (int) (p1.getPrice() -p2.getPrice()));
    sortProducts.forEach(System.out::println);

    System.out.println("FILTER: ---");
    // Filter products
    products.stream()
        .filter(p -> p.getPrice() > 20)
        .forEach(System.out::println);

    System.out.println("MAP: ---");
    Stream<String> strProducts = products.stream()
        .map(p -> p.getName() + ": " + p.getPrice());
    List<String > listStr = strProducts.collect(Collectors.toList());
    listStr.forEach(System.out::println);

    Product[] arr = products.stream()
        .filter(p -> p.getPrice() > 15)
        .toArray(Product[]::new);

    Arrays.stream(arr).forEach(System.out::println);

    // Convert Stream to List
//    List<Product> listProducts = filterProducts.collect(Collectors.toList());
//    listProducts.forEach(System.out::println);
  }
}

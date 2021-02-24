package com.agility.chapter6;

import com.agility.Product;

import java.util.*;

public class CollectionExample {
  public void testInitialize() {
    Collection<String> collection = List.of("S1", "S2", "S2");
//    collection.remove("S2");
    System.out.println("Collection List: " + String.join(",", collection));
    System.out.println("Compare two List: " + collection.equals(List.of("S2", "S2", "S1")));
    collection = Set.of("S3", "S4");
    System.out.println("Collection Set: " + collection);
    collection.forEach(item -> {
      System.out.println("Item:" + item);
    });
    System.out.println("Compare Set with List: " + collection.equals(List.of("S3", "S4")));
    System.out.println("Compare two Set: " + collection.equals(Set.of("S4", "S3")));
  }

  public void testMethod() {
    List<String> list = new ArrayList<String>();
    list.add("S1");
    list.addAll(List.of("s2", "S3"));
    System.out.println("List: "+ list);
    List<String> list1 = List.copyOf(list);
    System.out.println("list1: "+ list1);
    list.set(1, "S5");
    System.out.println("Get index 1: "+ list.get(1));
    list.sort(String.CASE_INSENSITIVE_ORDER);
    System.out.println("list 2: "+ list);
    System.out.println("list1 2: "+ list1);
    System.out.println("list hashCode: "+ list.hashCode());
  }

  public void testCompare() {
    Product product1 = new Product("Test1", "1");
    Product product2 = new Product("Test1", "1");
    System.out.println("hashCode 1: "+ product1.hashCode());
    System.out.println("hashCode 2: "+ product2.hashCode());
    System.out.println("Equals: "+ product2.equals(product1));

    List<Product> listProducts = new ArrayList<>();
    listProducts.addAll(List.of(product1, product2));
    System.out.println("Contains: "+ listProducts.contains(product1));

    Set<Product> products = new HashSet<>();
    products.addAll(List.of(product1, product2));
    System.out.println("products: "+ products);

    product2.setName("Abcd");
    listProducts.set(1, product2);
    System.out.println("listProducts: "+ listProducts);
  }

  public void testCollectionClass () {
    Product product1 = new Product("DAC1", "d");
    product1.setQuantity(4);
    Product product2 = new Product("ABC2", "c");
    product2.setQuantity(15);
    Product product3 = new Product("GHK3", "v");
    product3.setQuantity(30);
    Product product4 = new Product("QKD4", "a");
    product4.setQuantity(1);
    List<Product> listProducts = Arrays.asList( product1, product2, product3);
    List<Product> listProducts2 = Arrays.asList(product4, product2);
    Collections.copy(listProducts, listProducts2);
    System.out.println("List product: "+ listProducts);
    List<String> list = Arrays.asList("a", "X", "10", "20", "1", "2");
    Collections.sort(list);
    System.out.println("Sort list: "+ list);
//    Collections.sort(listProducts, new SortProductByName());
//    System.out.println("Sort list product1: "+ listProducts);

    try {
      List<Product> listProducts3 = this.cloneList(listProducts);
      Collections.sort(listProducts3, new SortProductByQuantity());
      listProducts3.sort(new SortProductByQuantity());
      System.out.println("Sort list product3: "+ listProducts3);
    } catch (CloneNotSupportedException e) {
      e.printStackTrace();
    }
  }

  private class SortProductByQuantity implements Comparator<Product> {
    @Override
    public int compare(Product o1, Product o2) {
      return o1.getQuantity() - o2.getQuantity();
    }
  }

  private class SortProductByName implements Comparator<Product> {

    @Override
    public int compare(Product o1, Product o2) {
      return o1.getName().compareTo(o2.getName());
    }
  }

  private static List<Product> cloneList(List<Product> list) throws CloneNotSupportedException {
    List<Product> clone = new ArrayList<>(list.size());
    for (Product item : list) {
      clone.add(item.clone());
    }
    return clone;
  }

}

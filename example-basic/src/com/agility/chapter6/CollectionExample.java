package com.agility.chapter6;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

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
}

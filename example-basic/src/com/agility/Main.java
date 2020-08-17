package com.agility;

import java.util.List;

public class Main {

    public static  void exampleFor() {
        List<List<String>> listOfLists = List.of(
                List.of("24", "16", "1", "2", "1"),
                List.of("43", "42", "31", "3", "3"),
                List.of("24", "22", "31", "2", "13")
        );

        String found = null;
        exit: for(List<String> l: listOfLists){
            for (String s: l){
                System.out.println(s + " ");
                if(s.contains("3")){
                    found = s;
                    break exit;
                } }
        }

        String checked = "";
        cont: for(List<String> l: listOfLists){
            for (String s: l){
                System.out.println(s + " ");
                if(s.contains("3")){
                    continue ;
                }
                checked += s + " ";
            }
        }
        System.out.println("Found " + found);
        System.out.println("checked " + checked);
    }
    public static void main(String[] args) {
        IProduct product = new Product("Phone", "This is Phone");
        ((Product) product).setName("New Phone");
        System.out.println("Quantity: " + ((Product) product).getName() + ":" + product.getQuantity());
        System.out.println("GET: " + product.get());
    }
}

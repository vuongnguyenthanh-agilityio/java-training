package com.agility;

public class Product implements IProduct {
  private String name;
  private String description;
  protected int quantity;

  public Product(String name, String description) {
    this.name = name;
    this.description = description;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  private String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public String get() {
    return this.getName() + this.test;
  }

  @Override
  public void add() {

  }
}

class PhoneProduct extends Product {

  public PhoneProduct(String name, String description) {
    super(name, description);
  }

  @Override
  public String getName() {
    return super.getName();
  }

  @Override
  public void setName(String name) {
    super.setName(name);
  }

  public void setQantity(int quantity) {
    this.quantity = quantity;
  }
}

class Iphone extends PhoneProduct {

  public Iphone(String name, String description) {
    super(name, description);
  }

  public int getQuantity() {
    return this.quantity;
  }
}

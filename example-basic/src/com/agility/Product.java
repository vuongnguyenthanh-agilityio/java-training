package com.agility;

import java.util.Objects;

public class Product implements IProduct, Cloneable {
  private String name;
  private String description;
  private int quantity;

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

  public void setQuantity(int quantity) {
    this.quantity = quantity;
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Product product = (Product) o;
    return name.equals(product.name) &&
        description.equals(product.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, description);
  }

  @Override
  public String toString() {
    return "Product{" +
        "name='" + name + '\'' +
        ", description='" + description + '\'' +
        ", quantity=" + quantity +
        '}';
  }

  @Override
  public Product clone() throws CloneNotSupportedException {
    Product clone = (Product) super.clone();

    return clone;
  }
}

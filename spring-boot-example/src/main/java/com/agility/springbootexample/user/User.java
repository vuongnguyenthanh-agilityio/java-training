package com.agility.springbootexample.user;

import com.agility.springbootexample.category.Category;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "user")
public class User {
  @Id
  private String id;
  private String name;
  private String email;
  private String phone;
  private Date createDate = new Date();
  private Location location;
  private List<String> interestCategories;
  private List<Category> categories;

  public User() {
  }

  public User(String id, String name, String email, String phone) {
    this.name = name;
    this.email = email;
    this.phone = phone;
    this.id = id;
  }

  public User(String id, String name, String email, String phone, Date createDate, Location location, List<String> categories) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.phone = phone;
    this.createDate = createDate;
    this.location = location;
    this.interestCategories = categories;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public Date getCreateDate() {
    return createDate;
  }

  public Location getLocation() {
    return location;
  }

  public void setLocation(Location location) {
    this.location = location;
  }

  public List<String> getInterestCategories() {
    return interestCategories;
  }

  public void setInterestCategories(List<String> categories) {
    this.interestCategories = categories;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  public List<Category> getCategories() {
    return categories;
  }

  public void setCategories(List<Category> categories) {
    this.categories = categories;
  }

  @Override
  public String toString() {
    return "User{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", email='" + email + '\'' +
        ", phone='" + phone + '\'' +
        ", createDate=" + createDate +
        '}';
  }
}

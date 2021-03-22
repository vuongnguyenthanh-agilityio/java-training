package com.agility.springbootexample.category;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "category")
public class Category {
  @Id
  private String id;
  private String name;
  private String description;
  private Date createDate = new Date();

  public Category(String id, String name, String description) {
    this.id = id;
    this.name = name;
    this.description = description;
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

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  @Override
  public String toString() {
    return "Category{" +
        "id='" + id + '\'' +
        ", name='" + name + '\'' +
        ", description='" + description + '\'' +
        ", createDate=" + createDate +
        '}';
  }
}

package com.agility.springbootexample.user;


import com.agility.springbootexample.product.Product;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository("UserRepository")
@Primary
public class UserRepository implements IUserRepository{
  private static List<User> users = new ArrayList<>();

  static {
    users.add(new User(1,"Vuong Nguyen", "vuong@gmail.com", "0963216949"));
  }
  @Override
  public List<User> getAll() {
    return this.users;
  }

  @Override
  public User getUser(int id) {
    User user = users.stream()
        .filter(i -> i.getId() == id)
        .findAny()
        .orElse(null);

    return user;
  }

  @Override
  public User updateUser(User user) {
    return null;
  }

  @Override
  public User deleteUser(String id) {
    return null;
  }
}

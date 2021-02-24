package com.agility.springbootexample.user;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository("UserRepository")
@Primary
public class  UserRepository {
  private static List<User> users = new ArrayList<>();

  static {
    users.add(new User("1","Vuong Nguyen", "vuong@gmail.com", "0963216949"));
  }
  public List<User> getAll() {
    return this.users;
  }

  public User getUser(String id) {
    User user = users.stream()
        .filter(i -> i.getId() == id)
        .findAny()
        .orElse(null);

    return user;
  }
}

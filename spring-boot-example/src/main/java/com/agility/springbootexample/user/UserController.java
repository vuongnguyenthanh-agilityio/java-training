package com.agility.springbootexample.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
  @Autowired
  private UserService userService;

  @GetMapping(path = "/users")
  public List<User> getAll() {
    return userService.getAll();
  }

  @GetMapping(path="/users/{id}")
  public User getProduct(@PathVariable int id) {
    User user = userService.getUser(id);
    return user;
  }
}

package com.agility.springbootexample.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

  @GetMapping(path = "/users")
  public String getUserName() {
    return "Vuong Nguyen";
  }
}

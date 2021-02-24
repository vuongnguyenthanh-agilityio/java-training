package com.agility.springbootexample.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
  @Autowired
  private UserService userService;
  private final Logger LOG = LoggerFactory.getLogger(getClass());
  @GetMapping(path = "/users")
  public ResponseEntity<List<User>> getAll(@RequestParam(required = false) String name) {
    List<User> users = userService.getAll(name);
    LOG.info(users.toString());
    if (users.isEmpty()) {
      return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    return new ResponseEntity<>(users, HttpStatus.OK);
  }

  @PostMapping(path="/users")
  public ResponseEntity<User> createUser(@RequestBody User user) {
    System.out.println("User: "+ user.toString());
    User newUser = userService.createUser(user);
    return new ResponseEntity<>(newUser, HttpStatus.CREATED);
  }

  @GetMapping(path="/users/{id}")
  public ResponseEntity<User> getUser(@PathVariable String id) {
    User user = userService.getUser(id);

    return new ResponseEntity<>(user, HttpStatus.OK);
  }

  @PutMapping(path="/users/{id}")
  public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody User user) {
    User newUser = userService.updateUser(id, user);

    return new ResponseEntity<>(newUser, HttpStatus.OK);
  }

  @DeleteMapping(path="/users/{id}")
  public ResponseEntity<User> deleteUser(@PathVariable String id) {
    LOG.info(id);
    User user = userService.deleteUser(id);

    return new ResponseEntity<>(user, HttpStatus.OK);
  }
}

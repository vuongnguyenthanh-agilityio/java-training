package com.agility.marketservice.controller;

import com.agility.marketservice.model.User;
import com.agility.marketservice.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Vuong Nguyen
 *
 * TODO: This class only gets data for testing. It doesn't include in the practice scope
 */
@RestController
public class UserController {
  @Autowired
  private IUserRepository userRepository;
  @GetMapping(path = "/api/users")
  public ResponseEntity<List<User>> getAll() {
    List<User> user = userRepository.findAll();

    return new ResponseEntity<>(user, HttpStatus.OK);
  }
}

package com.agility.marketservice.controller;

import com.agility.marketservice.model.User;
import com.agility.marketservice.repository.IUserRepository;
import com.agility.marketservice.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
  @Autowired
  private IUserRepository userRepository;
  @GetMapping(path = "/")
  public ResponseEntity<List<User>> getAll() {
    List<User> user = userRepository.findAll();

    return new ResponseEntity<>(user, HttpStatus.OK);
  }
}

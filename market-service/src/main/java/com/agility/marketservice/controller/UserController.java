package com.agility.marketservice.controller;

import com.agility.marketservice.dto.UserDto;
import com.agility.marketservice.model.User;
import com.agility.marketservice.repository.IUserRepository;
import com.agility.marketservice.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

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
  public ResponseEntity<List<UserDto>> getAll() {
    List<User> users = userRepository.findAll();
    List<UserDto> dtoUsers = users.stream()
        .map(u -> Mapper.convertUserDto(u))
        .collect(Collectors.toList());

    return new ResponseEntity<>(dtoUsers, HttpStatus.OK);
  }
}

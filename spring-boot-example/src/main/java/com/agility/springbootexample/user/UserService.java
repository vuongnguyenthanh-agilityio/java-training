package com.agility.springbootexample.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
  @Autowired
//  @Qualifier("UserRepository")
  private IUserRepository userRepository;

  public List<User> getAll() {
    return userRepository.getAll();
  }

  public int totalUser() {
    return userRepository.getAll().size();
  }

  public User getUser(int id) {
    return userRepository.getUser(id);
  }
}

package com.agility.marketservice.service;

import com.agility.marketservice.model.User;
import com.agility.marketservice.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {
  @Autowired
  private IUserRepository userRepository;

  @Override
  public User getCurrentUser() {
    String email = SecurityContextHolder.getContext().getAuthentication().getName();
    if (email == null) return null;

    return userRepository.findByEmail(email);
  }

  @Override
  public User findUserByEmail(String email) {
    return userRepository.findByEmail(email);
  }
}

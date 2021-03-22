package com.agility.marketservice.service;

import com.agility.marketservice.model.User;
import com.agility.marketservice.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
  private final IUserRepository userRepository;

  @Override
  public User getCurrentUser() {
    if (SecurityContextHolder.getContext().getAuthentication() == null) return null;

    String email = SecurityContextHolder.getContext().getAuthentication().getName();
    if (email == null) return null;

    return userRepository.findByEmail(email);
  }

  @Override
  public User findUserByEmail(String email) {
    return userRepository.findByEmail(email);
  }
}

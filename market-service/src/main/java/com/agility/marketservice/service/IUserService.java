package com.agility.marketservice.service;

import com.agility.marketservice.model.User;

public interface IUserService {
  User getCurrentUser();
  User findUserByEmail(String email);
}

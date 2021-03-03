package com.agility.marketservice.service;

import com.agility.marketservice.model.User;

public interface IUserService {
  User findUserByEmail(String email);
}

package com.agility.marketservice.config;

import com.agility.marketservice.model.User;
import com.agility.marketservice.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserAuditing implements AuditorAware<User> {
  @Autowired
  private IUserService iUserService;

  @Override
  public Optional<User> getCurrentAuditor() {
    User user = iUserService.getCurrentUser();
    return Optional.of(user);
  }
}

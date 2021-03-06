package com.agility.marketservice.config;

import com.agility.marketservice.dto.UserDto;
import com.agility.marketservice.model.User;
import com.agility.marketservice.service.IUserService;
import com.agility.marketservice.util.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class UserAuditing implements AuditorAware<User> {
  private final IUserService iUserService;

  @Override
  public Optional<User> getCurrentAuditor() {
    User user = iUserService.getCurrentUser();
    if (user == null) return null;
    // Handle convert twice to remove properties unused
    UserDto userDto = Mapper.convertUserDto(user);
    return Optional.of(Mapper.convertUserEntity(userDto));
  }
}

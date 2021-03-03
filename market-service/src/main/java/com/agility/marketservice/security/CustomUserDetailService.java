package com.agility.marketservice.security;

import com.agility.marketservice.model.User;
import com.agility.marketservice.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailService implements UserDetailsService {
  @Autowired
  private IUserService userService;
  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User user = userService.findUserByEmail(email);
    if (user == null) {
      throw new UsernameNotFoundException("The email: "+ email + "not found.");
    }
    List<GrantedAuthority> authorities = new ArrayList<>();
    authorities.add(new SimpleGrantedAuthority(user.getRole().getCode().name()));
    return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
  }
}

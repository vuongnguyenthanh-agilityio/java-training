package com.agility.oauthservice.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class JWTConfigValue {
  @Value("${jwt.secret}")
  private String jwtSecret;
  @Value("${jwt.expiration.time}")
  private String jwtExpirationTime;

  public long getJwtExpirationTime() {
    return Long.parseLong(jwtExpirationTime);
  }
}

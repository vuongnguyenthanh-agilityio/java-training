package com.agility.marketservice.security;

public class SecurityConstants {
  public static final String JWT_SECRET = "n2r5u8x/A%D*G-KaPdSgVkYp3s6v9y$B&E(H+MbQeThWmZq4t7w!z%C*F-J@NcRf";
  public static final long EXPIRATION_TIME = 36_000_000; // 600 minutes
  public static final String TOKEN_PREFIX = "Bearer ";
  public static final String HEADER_STRING = "Authorization";
  public static final String ROLES = "Roles";
}

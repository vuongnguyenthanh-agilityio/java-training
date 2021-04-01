package com.agility.oauthservice.security;

import com.agility.oauthservice.dto.UserDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Vuong Nguyen
 */
@Setter
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
  public static final String TOKEN_PREFIX = "Bearer ";
  public static final String HEADER_STRING = "Authorization";
  public static final String ROLES = "Roles";
  private JWTConfigValue jwtConfigValue;

  private AuthenticationManager authenticationManager;
  public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTConfigValue jwtConfigValue) {
    this.authenticationManager = authenticationManager;
    this.jwtConfigValue = jwtConfigValue;
    this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/v1/api/auth", "POST"));
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request,
                                              HttpServletResponse response) throws AuthenticationException {
    UserDto user = new UserDto();
    try {
      user = new ObjectMapper().readValue(request.getInputStream(), UserDto.class);
    } catch (IOException e) {
      e.printStackTrace();
    }

    return authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            user.getEmail(),
            user.getPassword(),
            new ArrayList<>()
        )
    );
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request,
                                          HttpServletResponse response,
                                          FilterChain chain,
                                          Authentication authResult) throws IOException, ServletException {
    org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authResult.getPrincipal();
    String userId = user.getUsername();
    if (userId != null && userId.length() > 0) {
      Claims claims = Jwts.claims().setSubject(userId);
      byte[] signingKey = jwtConfigValue.getJwtSecret().getBytes();
      List<String> roles = new ArrayList<>();
      user.getAuthorities().stream().forEach(authority -> roles.add(authority.getAuthority()));
      claims.put(ROLES, roles);
      String token = Jwts.builder()
          .setClaims(claims)
          .setIssuedAt(new Date())
          .setExpiration(new Date(System.currentTimeMillis() + jwtConfigValue.getJwtExpirationTime()))
          .signWith(SignatureAlgorithm.HS512, signingKey)
          .compact();

      response.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
      response.getWriter().write(token);
    }
  }
}

package com.agility.marketservice.security;

import com.agility.marketservice.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Vuong Nguyen
 */
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
  private AuthenticationManager authenticationManager;

  public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
    this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/api/auth", "POST"));
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request,
                                              HttpServletResponse response) throws AuthenticationException {
    try {
      User user = new ObjectMapper().readValue(request.getInputStream(), User.class);
      System.out.println("USER: " + user.toString());

      return authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
              user.getEmail(),
              user.getPassword(),
              new ArrayList<>()
          )
      );
    } catch (IOException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request,
                                          HttpServletResponse response,
                                          FilterChain chain,
                                          Authentication authResult) throws IOException, ServletException {
    org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authResult.getPrincipal();
    String login = user.getUsername();
    System.out.println("login: " + login);
    if (login != null && login.length() > 0) {
      Claims claims = Jwts.claims().setSubject(login);
      byte[] signingKey = SecurityConstants.JWT_SECRET.getBytes();

      String token = token = Jwts.builder()
          .setIssuedAt(new Date())
          .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
          .signWith(SignatureAlgorithm.HS512, signingKey)
          .compact();

      response.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token);
      response.getWriter().write(token);
    }
  }
}

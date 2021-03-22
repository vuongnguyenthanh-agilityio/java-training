package com.agility.marketservice.security;

import com.agility.marketservice.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

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
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
  private AuthenticationManager authenticationManager;
  public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
    this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/api/auth", "POST"));
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request,
                                              HttpServletResponse response) throws AuthenticationException {
    User user = new User();
    try {
      user = new ObjectMapper().readValue(request.getInputStream(), User.class);
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
    String login = user.getUsername();
    if (login != null && login.length() > 0) {
      Claims claims = Jwts.claims().setSubject(login);
      byte[] signingKey = SecurityConstants.JWT_SECRET.getBytes();
      List<String> roles = new ArrayList<>();
      user.getAuthorities().stream().forEach(authority -> roles.add(authority.getAuthority()));
      claims.put(SecurityConstants.ROLES, roles);
      String token = token = Jwts.builder()
          .setClaims(claims)
          .setIssuedAt(new Date())
          .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
          .signWith(SignatureAlgorithm.HS512, signingKey)
          .compact();

      response.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token);
      response.getWriter().write(token);
    }
  }
}

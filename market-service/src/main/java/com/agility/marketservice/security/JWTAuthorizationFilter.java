package com.agility.marketservice.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
  public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
    super(authenticationManager);
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request,
                                  HttpServletResponse response,
                                  FilterChain chain) throws IOException, ServletException {
    String token = request.getHeader(SecurityConstants.HEADER_STRING);
    if (token == null || !token.startsWith(SecurityConstants.TOKEN_PREFIX)) {
      chain.doFilter(request, response);
      return;
    }

    try {
      UsernamePasswordAuthenticationToken authentication = getAuthentication(token);
      SecurityContextHolder.getContext().setAuthentication(authentication);
    } catch (Exception ex) {
      request.setAttribute("exception", ex);
    }

    chain.doFilter(request, response);
  }

  private  UsernamePasswordAuthenticationToken getAuthentication(String token) {
    Claims claims = Jwts.parser()
        .setSigningKey(SecurityConstants.JWT_SECRET.getBytes())
        .parseClaimsJws(token.replace(SecurityConstants.TOKEN_PREFIX, ""))
        .getBody();

    String userName = claims.getSubject();
    if (userName == null) return null;

    List<String> roles = (ArrayList<String>) claims.get(SecurityConstants.ROLES);
    System.out.println("Roles: "+ roles.toString());
    // Then convert Roles to GrantedAuthority Object for injecting
    ArrayList<GrantedAuthority> list = new ArrayList<>();
    if (roles != null) {
      for (String a : roles) {
        GrantedAuthority g = new SimpleGrantedAuthority(a);
        list.add(g);
      }
    }

    return new UsernamePasswordAuthenticationToken(userName, null, list);
  }
}

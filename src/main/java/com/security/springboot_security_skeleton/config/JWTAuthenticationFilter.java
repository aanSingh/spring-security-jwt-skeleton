package com.security.springboot_security_skeleton.config;

import com.security.springboot_security_skeleton.service.CustomUserDetailService;
import com.security.springboot_security_skeleton.service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {
  private final JWTService jwtService;
  private final CustomUserDetailService customUserDetailService;
  HandlerExceptionResolver handlerExceptionResolver;

  public JWTAuthenticationFilter(
      JWTService jwtService,
      CustomUserDetailService customUserDetailService,
      HandlerExceptionResolver handlerExceptionResolver) {
    this.jwtService = jwtService;
    this.customUserDetailService = customUserDetailService;
    this.handlerExceptionResolver = handlerExceptionResolver;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    final String authHeader = request.getHeader("Authorization");

    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }

    try {
      final String jwt = authHeader.substring(7);
      final String userEmail = jwtService.extractUsername(jwt);

      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

      if (userEmail != null && authentication == null) {
        UserDetails userDetails = this.customUserDetailService.loadUserByUsername(userEmail);

        if (jwtService.isTokenValid(jwt, userDetails)) {
          UsernamePasswordAuthenticationToken authToken =
              new UsernamePasswordAuthenticationToken(
                  userDetails, null, userDetails.getAuthorities());

          authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
          SecurityContextHolder.getContext().setAuthentication(authToken);
        }
      }

      filterChain.doFilter(request, response);
    } catch (Exception exception) {
      handlerExceptionResolver.resolveException(request, response, null, exception);
    }
  }
}

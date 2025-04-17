package com.openclassrooms.mddapi.config;

import com.openclassrooms.mddapi.auth.filter.JwtAuthenticationFilter;
import com.openclassrooms.mddapi.auth.service.CustomUserDetailsService;
import com.openclassrooms.mddapi.auth.service.JwtService;
import com.openclassrooms.mddapi.utils.PublicRoutes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.Arrays;

/**
 * Configuration class for enabling and customizing Spring Security within the application.
 * It defines the security configurations, including the authentication manager,
 * security filter chain, and password encoder, to manage and secure access to the APIs.
 */
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

  private final JwtService jwtService;
  private final UserDetailsService userDetailsService;


  public SpringSecurityConfig(JwtService jwtService, UserDetailsService userDetailsService) {
    this.jwtService = jwtService;
    this.userDetailsService = userDetailsService;
  }

  /**
   * Configures the security filter chain for the application.
   * The configuration includes disabling CSRF protection, managing authentication
   * and authorization rules, setting session management policy to stateless,
   * and allowing access to specific public endpoints.
   *
   * @param http the {@link HttpSecurity} object to customize the security configuration
   * @return a configured {@link SecurityFilterChain} instance
   * @throws Exception if an error occurs during configuration
   */
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                    // allow access to some endpoints without authentication and secure the rest
                    .requestMatchers(
                            Arrays.stream(PublicRoutes.PUBLIC_URLS)
                                    .map(AntPathRequestMatcher::new)
                                    .toArray(RequestMatcher[]::new)
                    ).permitAll()
                    .anyRequest().authenticated())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            // add JWT filter to validate token and set authentication
            .addFilterBefore(new JwtAuthenticationFilter(jwtService, userDetailsService), UsernamePasswordAuthenticationFilter.class)
            .build();
  }

  /**
   * Provides a bean of type {@link PasswordEncoder} that uses the BCrypt hashing function.
   * This encoder is utilized for encoding and verifying passwords in a secure manner.
   *
   * @return an instance of {@link BCryptPasswordEncoder} for password encryption
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  /**
   * Provides an {@link AuthenticationManager} bean for managing authentication in the application.
   * This method sets up a {@link DaoAuthenticationProvider} with a custom user details service and
   * password encoder for validating user credentials.
   *
   * @param userDetailsService the {@link CustomUserDetailsService} used to retrieve user-specific data
   * @return an {@link AuthenticationManager} instance configured with the specified authentication provider
   */
  @Bean
  public AuthenticationManager authenticationManager(CustomUserDetailsService userDetailsService) {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(userDetailsService);
    authProvider.setPasswordEncoder(passwordEncoder());
    return new ProviderManager(authProvider);
  }
}

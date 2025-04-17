package com.openclassrooms.mddapi.auth.service;

import com.openclassrooms.mddapi.auth.dto.LoginDto;
import com.openclassrooms.mddapi.errors.LoginFailedException;
import com.openclassrooms.mddapi.user.model.User;
import com.openclassrooms.mddapi.user.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



@Service
public class AuthService {
  private final UserService userService;
  private final JwtService jwtService;
  private final PasswordEncoder passwordEncoder;

  public AuthService(UserService userService, JwtService jwtService, PasswordEncoder passwordEncoder) {
    this.userService = userService;
    this.jwtService = jwtService;
    this.passwordEncoder = passwordEncoder;
  }

  /**
   * Logs a user into the system by validating their email and password credentials.
   * If authentication is successful, a JWT token is generated and returned.
   *
   * @param loginDto An object containing the user's email and password used for authentication.
   * @return A JWT token for the authenticated user.
   * @throws LoginFailedException If the email does not correspond to a registered user or
   *                              if the provided password is incorrect.
   */
  public String login(LoginDto loginDto) {
    // try to find user by email. if user not found or password is incorrect
    User user = userService.findUserByEmail(loginDto.getUsername());
    if (user == null || !passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
      throw new LoginFailedException("Email ou mot de passe incorrect.");
    }

    // generate token and return it
    return jwtService.generateToken(user.getEmail());
  }
}

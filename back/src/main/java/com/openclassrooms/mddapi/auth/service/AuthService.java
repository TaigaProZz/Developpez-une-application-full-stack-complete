package com.openclassrooms.mddapi.auth.service;

import com.openclassrooms.mddapi.auth.dto.LoginDto;
import com.openclassrooms.mddapi.auth.dto.RegisterDto;
import com.openclassrooms.mddapi.errors.EmailAlreadyUsedException;
import com.openclassrooms.mddapi.errors.LoginFailedException;
import com.openclassrooms.mddapi.errors.UserNotFoundException;
import com.openclassrooms.mddapi.user.dto.GetUserDto;
import com.openclassrooms.mddapi.user.model.User;
import com.openclassrooms.mddapi.user.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;


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

  /**
   * Registers a new user by saving their details and generating a JWT token.
   *
   * @param registerDto An object containing the registration details of the user,
   *                    including email, password, and name.
   * @return A JWT token for the successfully registered user.
   */
  public String registerUser(RegisterDto registerDto) {
    // check if email is already used
    if (this.userService.findUserByEmail(registerDto.getEmail()) != null) {
      throw new EmailAlreadyUsedException("Email déjà utilisée.");
    }

    if (this.userService.findByUsername(registerDto.getUsername()) != null) {
      throw new EmailAlreadyUsedException("Nom d'utilisateur déjà utilisé.");
    }

    // save user on db and return a token
    this.userService.saveUser(registerDto);
    return jwtService.generateToken(registerDto.getEmail());
  }

  /**
   * Retrieves the currently logged-in user's details based on the provided {@link Principal}.
   * The method fetches the user from the database using their email, which is derived from the principal.
   * If the user is found, their details are mapped into a {@link GetUserDto}.
   *
   * @param principal The security principal representing the currently authenticated user,
   *                  containing their authentication details such as email.
   * @return A {@link GetUserDto} object containing the details of the currently logged-in user,
   *         including their ID, email, name, creation timestamp, and last updated timestamp.
   * @throws UserNotFoundException If no user is found in the database with the email obtained from the principal.
   */
  public GetUserDto me(Principal principal) {
    // get current user and check if user is valid
    User user = userService.findUserByEmail(principal.getName());
    if (user == null) {
      throw new UserNotFoundException("Utilisateur introuvable.");
    }

    // map to dto
    GetUserDto getUserDto = new GetUserDto();
    getUserDto.setId(user.getId());
    getUserDto.setEmail(user.getEmail());
    getUserDto.setUsername(user.getUsername());
    getUserDto.setCreated_at(user.getCreatedAt());
    getUserDto.setUpdated_at(user.getUpdatedAt());
    return getUserDto;
  }

}

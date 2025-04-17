package com.openclassrooms.mddapi.user.service;

import com.openclassrooms.mddapi.auth.dto.RegisterDto;
import com.openclassrooms.mddapi.errors.UserNotFoundException;
import com.openclassrooms.mddapi.user.model.User;
import com.openclassrooms.mddapi.user.repository.UserRepository;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Data
@Service
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  /**
   * Finds a user by their email address.
   *
   * @param email The email address of the user to be searched.
   * @return The User object if a user with the given email exists; otherwise, null.
   */
  public User findUserByEmail(String email) {
    // try to find user by email, or assign null
    return this.userRepository.findByEmail(email).orElse(null);
  }

  /**
   * Retrieves a user by their unique identifier.
   *
   * @param id The unique identifier of the user to find.
   * @return The User object matching the given identifier.
   * @throws UserNotFoundException if no user with the specified id is found.
   */
  public User findUserById(Long id) {
    // try to find user by id, and throw error if not found
    User user = this.userRepository.findById(id).orElse(null);
    if (user == null) {
      throw new UserNotFoundException("Utilisateur introuvable.");
    }

    // return user if found
    return user;
  }
}

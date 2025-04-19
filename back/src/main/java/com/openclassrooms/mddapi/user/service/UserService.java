package com.openclassrooms.mddapi.user.service;

import com.openclassrooms.mddapi.auth.dto.RegisterDto;
import com.openclassrooms.mddapi.errors.EmailAlreadyUsedException;
import com.openclassrooms.mddapi.errors.UserNotFoundException;
import com.openclassrooms.mddapi.user.dto.GetUserDto;
import com.openclassrooms.mddapi.user.dto.UpdateUserDto;
import com.openclassrooms.mddapi.user.model.User;
import com.openclassrooms.mddapi.user.repository.UserRepository;
import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;
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
     * Finds a user by their username.
     *
     * @param username The username of the user to be searched.
     * @return The User object if a user with the given username exists; otherwise, null.
     */
    public User findByUsername(String username) {
        // try to find user by username, or assign null
        return this.userRepository.findByUsername(username).orElse(null);
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

  /**
   * Saves a new user based on the registration details provided.
   * The password is encoded before storing in the database.
   *
   * @param registerDto An object containing the registration details of the user,
   *                    including email, password, and name.
   */
  public void saveUser(RegisterDto registerDto) {
    // bind to user
    User user = new User();
    user.setEmail(registerDto.getEmail());
    user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
    user.setUsername(registerDto.getUsername());

    // and save it in db
    this.userRepository.save(user);
  }

  /**
   * Updates the user information in the database.
   * @param updateUserDto The DTO containing the updated user information.
   * @param currentUser The currently authenticated user.
   *
   */
  public GetUserDto updateUser(UpdateUserDto updateUserDto, UserDetails currentUser) {
    // find the user by email
    User user = this.userRepository.findByEmail(currentUser.getUsername()).orElse(null);
    // check if user is valid
    if (user == null) {
        throw new UserNotFoundException("Utilisateur introuvable.");
    }
    // check if username is already used
    if (!user.getUsername().equals(updateUserDto.getUsername()) &&
      userRepository.findByUsername(updateUserDto.getUsername()).isPresent()) {
      throw new EmailAlreadyUsedException("Nom d'utilisateur déjà utilisé.");
    }

    // check if email is already used
    if (!user.getEmail().equals(updateUserDto.getEmail()) &&
      userRepository.findByEmail(updateUserDto.getEmail()).isPresent()) {
      throw new EmailAlreadyUsedException("Email déjà utilisé.");
    }

    // update user information
    user.setUsername(updateUserDto.getUsername());
    user.setEmail(updateUserDto.getEmail());
    if (updateUserDto.getPassword() != null && !updateUserDto.getPassword().isBlank()) {
      String pwd = updateUserDto.getPassword();
      if (pwd.length() < 8 ||
              !pwd.matches(".*[A-Z].*") ||
              !pwd.matches(".*[a-z].*") ||
              !pwd.matches(".*\\d.*") ||
              !pwd.matches(".*[!@#$%^&*(),.?\":{}|<>].*")) {
        throw new IllegalArgumentException("Le mot de passe ne respecte pas les critères de sécurité.");
      }

      user.setPassword(passwordEncoder.encode(pwd));
    }
    this.userRepository.save(user);

    GetUserDto getUserDto = new GetUserDto();
    getUserDto.setId(user.getId());
    getUserDto.setEmail(user.getEmail());
    getUserDto.setUsername(user.getUsername());
    getUserDto.setCreated_at(user.getCreatedAt());
    getUserDto.setUpdated_at(user.getUpdatedAt());
    return getUserDto;
  }

}

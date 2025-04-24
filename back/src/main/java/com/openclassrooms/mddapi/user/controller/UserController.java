package com.openclassrooms.mddapi.user.controller;

import com.openclassrooms.mddapi.user.dto.GetUserDto;
import com.openclassrooms.mddapi.user.dto.UpdateUserDto;
import com.openclassrooms.mddapi.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/{id}")
  public ResponseEntity<GetUserDto> getUserById(@PathVariable Long id) {
    // try to find user by id
    GetUserDto user = userService.findUserById(id);

    // and return it
    return ResponseEntity.ok(user);
  }

  @PutMapping()
  public ResponseEntity<GetUserDto> updateUser(@RequestBody @Valid UpdateUserDto updateUserDto, @AuthenticationPrincipal UserDetails currentUser) {
    return ResponseEntity.ok(userService.updateUser(updateUserDto, currentUser));
  }
}

package com.openclassrooms.mddapi.auth.controller;

import com.openclassrooms.mddapi.auth.dto.LoginDto;
import com.openclassrooms.mddapi.auth.dto.RegisterDto;
import com.openclassrooms.mddapi.auth.dto.TokenDto;
import com.openclassrooms.mddapi.auth.service.AuthService;
import com.openclassrooms.mddapi.user.dto.GetUserDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  private final AuthService authService;

  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @PostMapping("/login")
  public ResponseEntity<TokenDto> login(@RequestBody LoginDto loginDto) {
    // try to log in and get token
    String token = this.authService.login(loginDto);
    return ResponseEntity.ok(new TokenDto(token));
  }

  @PostMapping("/register")
  public ResponseEntity<TokenDto> register(@RequestBody @Valid RegisterDto registerDto) {
    // save user and get the token
    String token = authService.registerUser(registerDto);
    return ResponseEntity.ok(new TokenDto(token));
  }

  // return authenticated user infos
  @GetMapping("/me")
  public ResponseEntity<GetUserDto> me(Principal principal) {
    // get user infos
    GetUserDto getUserDto = authService.me(principal);
    return ResponseEntity.ok(getUserDto);
  }
}

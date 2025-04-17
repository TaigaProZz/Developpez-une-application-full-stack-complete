package com.openclassrooms.mddapi.auth.controller;

import com.openclassrooms.mddapi.auth.dto.LoginDto;
import com.openclassrooms.mddapi.auth.dto.TokenDto;
import com.openclassrooms.mddapi.auth.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



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
}

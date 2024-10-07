package com.ssafyhome.controller;

import com.ssafyhome.model.service.JWTService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

  private final JWTService jwtService;

  public AuthController(JWTService jwtService) {

    this.jwtService = jwtService;
  }

  @PostMapping("/reissue")
  public ResponseEntity<?> reissue(
      @CookieValue(value = "refreshToken", defaultValue = "no_refresh_token")
      String refreshToken
  ) {

    return jwtService.reissueRefreshToken(refreshToken);
  }
}

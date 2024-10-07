package com.ssafyhome.controller;

import com.ssafyhome.model.service.JWTService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(
    name = "Auth Controller",
    description = "RefreshToken 재발급"
)
@RestController
@RequestMapping("/auth")
public class AuthController {

  private final JWTService jwtService;

  public AuthController(JWTService jwtService) {

    this.jwtService = jwtService;
  }

  @Operation(
      summary = "refresh token을 재발급함",
      description = "Authorization에 access token, Cookie에 refresh token을 삽입하여 반환"
  )
  @PostMapping("/reissue")
  public ResponseEntity<?> reissue(
      @Parameter(
          name = "refresh token",
          description = "access token을 재발급학기 위한 jwt토큰"
      )
      @CookieValue(value = "refreshToken", defaultValue = "no_refresh_token")
      String refreshToken
  ) {

    return jwtService.reissueRefreshToken(refreshToken);
  }
}

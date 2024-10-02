package com.ssafyhome.model.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface JWTService {

  ResponseEntity<?> reissueRefreshToken(String refreshToken);

  void saveRefreshTokenToRedis(String refreshToken, String userId);

  String checkRefreshTokenError(String refreshToken);

  ResponseEntity<?> setTokens(String userId, String userEmail);

  String getRefreshToken(HttpServletRequest request);
}

package com.ssafyhome.model.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface JWTService {

  ResponseEntity<?> reissueRefreshToken(String refreshToken);

  void saveRefreshTokenToRedis(String refreshToken, String userSeq);

  String checkRefreshTokenError(String refreshToken);

  ResponseEntity<?> setTokens(String userSeq, String userEmail);

  String getRefreshToken(HttpServletRequest request);
}

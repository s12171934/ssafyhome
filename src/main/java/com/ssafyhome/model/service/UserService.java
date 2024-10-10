package com.ssafyhome.model.service;

import com.ssafyhome.model.dto.UserDto;
import org.springframework.http.ResponseEntity;

public interface UserService {

  ResponseEntity<?> register(UserDto userDto);
  ResponseEntity<?> sendEmail(String email);
}

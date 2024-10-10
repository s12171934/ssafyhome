package com.ssafyhome.model.service;

import com.ssafyhome.model.dto.FindUserDto;
import com.ssafyhome.model.dto.PasswordDto;
import com.ssafyhome.model.dto.UserDto;
import com.ssafyhome.model.dto.UserSearchDto;
import com.ssafyhome.model.dto.entity.mysql.UserEntity;
import com.ssafyhome.model.dto.entity.redis.EmailSecretEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {

  ResponseEntity<?> register(UserDto userDto);
  ResponseEntity<?> sendEmail(String email);
  ResponseEntity<?> findUserId(FindUserDto findUserDto);
  ResponseEntity<?> findPassword(FindUserDto findUserDto);
  ResponseEntity<?> changePassword(String userSeq, PasswordDto passwordDto);
  ResponseEntity<?> checkEmailSecret(EmailSecretEntity emailSecretEntity);
  ResponseEntity<?> checkIdDuplicate(String id);
  ResponseEntity<?> updateUser(UserEntity userEntity);
  ResponseEntity<?> deleteUser(String userSeq);
  ResponseEntity<List<UserDto>> getUserList(UserSearchDto userSearchDto);
  ResponseEntity<UserDto> getUserInfo(String userSeq);
}

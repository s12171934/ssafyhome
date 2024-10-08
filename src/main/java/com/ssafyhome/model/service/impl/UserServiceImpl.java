package com.ssafyhome.model.service.impl;

import com.ssafyhome.model.dao.mapper.UserMapper;
import com.ssafyhome.model.dto.UserDto;
import com.ssafyhome.model.dto.entity.mysql.UserEntity;
import com.ssafyhome.model.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  private final BCryptPasswordEncoder passwordEncoder;
  private final UserMapper userMapper;

  public UserServiceImpl(
      BCryptPasswordEncoder passwordEncoder,
      UserMapper userMapper
  ) {

    this.passwordEncoder = passwordEncoder;
    this.userMapper = userMapper;
  }


  @Override
  public ResponseEntity<?> register(UserDto userDto) {

    if(!checkPassword(userDto)) {
      return new ResponseEntity<>("Incorrect password", HttpStatus.BAD_REQUEST);
    }

    UserEntity userEntity = new UserEntity();
    userEntity.setUserId(userDto.getUserId());
    userEntity.setUserPw(passwordEncoder.encode(userDto.getUserPassword()));
    userEntity.setUserEmail(userDto.getUserEmail());
    try {
      userMapper.insertUser(userEntity);
      return new ResponseEntity<>(HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  private boolean checkPassword(UserDto userDto) {

    return userDto.getUserPassword().equals(userDto.getUserPasswordConfirm());
  }
}

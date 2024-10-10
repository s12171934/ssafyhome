package com.ssafyhome.model.service.impl;

import com.ssafyhome.model.dao.mapper.UserMapper;
import com.ssafyhome.model.dto.FindUserDto;
import com.ssafyhome.model.dto.PasswordDto;
import com.ssafyhome.model.dto.UserDto;
import com.ssafyhome.model.dto.UserSearchDto;
import com.ssafyhome.model.dto.entity.mysql.UserEntity;
import com.ssafyhome.model.dto.entity.redis.EmailSecretEntity;
import com.ssafyhome.model.service.UserService;
import com.ssafyhome.util.SecretUtil;
import jakarta.mail.internet.MimeMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

  private final BCryptPasswordEncoder passwordEncoder;
  private final UserMapper userMapper;
  private final SecretUtil secretUtil;
  private final JavaMailSender mailSender;

  public UserServiceImpl(
      BCryptPasswordEncoder passwordEncoder,
      UserMapper userMapper,
      SecretUtil secretUtil,
      JavaMailSender mailSender
  ) {

    this.passwordEncoder = passwordEncoder;
    this.userMapper = userMapper;
    this.secretUtil = secretUtil;
    this.mailSender = mailSender;
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

  @Override
  public ResponseEntity<?> sendEmail(String email) {

    String secret = secretUtil.makeRandomString(15);
    MimeMessage mimeMessage = mailSender.createMimeMessage();

    try {

      MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
      mimeMessageHelper.setTo(email);
      mimeMessageHelper.setSubject("SSAFY HOME 이메일 인증");
      mimeMessageHelper.setText(secret, true);
      mailSender.send(mimeMessage);
      secretUtil.addSecretOnRedis(email, secret);
      return new ResponseEntity<>("send Email success", HttpStatus.CREATED);

    } catch (Exception e) {

      e.printStackTrace();
      return new ResponseEntity<>("send Email failed", HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @Override
  public ResponseEntity<?> findUserId(FindUserDto findUserDto) {

    String userId = userMapper.getIdByNameAndEmail(findUserDto);
    if (userId == null) {
      return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
    } else {
      return new ResponseEntity<>(userId, HttpStatus.OK);
    }
  }

  @Override
  public ResponseEntity<?> findPassword(FindUserDto findUserDto) {

    if (userMapper.isUserExist(findUserDto)) {
      return sendEmail(findUserDto.getUserEmail());
    } else {
      return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
    }
  }

  @Override
  public ResponseEntity<?> changePassword(String userSeq, PasswordDto passwordDto) {

    if (userSeq == null) {
      userSeq = SecurityContextHolder.getContext().getAuthentication().getName();
    } else {
      try {
        userSeq = secretUtil.decrypt(userSeq);
        if(!userMapper.checkPassword(
            userSeq,
            passwordEncoder.encode(passwordDto.getOldPassword()))
        ) {
          return new ResponseEntity<>("Incorrect password", HttpStatus.BAD_REQUEST);
        }
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }

    if (checkPassword(passwordDto)) {
      userMapper.patchPassword(userSeq, passwordEncoder.encode(passwordDto.getNewPassword()));
      return new ResponseEntity<>("Password changed successfully", HttpStatus.OK);
    } else {
      return new ResponseEntity<>("Incorrect password", HttpStatus.BAD_REQUEST);
    }
  }

  @Override
  public ResponseEntity<?> checkEmailSecret(EmailSecretEntity emailSecretEntity) {

    if (secretUtil.checkSecret(emailSecretEntity.getEmail(), emailSecretEntity.getSecret())) {
      secretUtil.removeSecretOnRedis(emailSecretEntity.getEmail());
      return new ResponseEntity<>("check email secret successfully", HttpStatus.OK);
    } else {
      return new ResponseEntity<>("check email secret failed", HttpStatus.BAD_REQUEST);
    }
  }

  @Override
  public ResponseEntity<?> checkIdDuplicate(String id) {

    UserEntity userEntity = userMapper.getUserById(id);
    if (userEntity == null) {
      return new ResponseEntity<>("usable user id", HttpStatus.OK);
    } else {
      return new ResponseEntity<>("already user exist", HttpStatus.BAD_REQUEST);
    }
  }

  @Override
  public ResponseEntity<?> updateUser(UserEntity userEntity) {

    userMapper.updateUser(userEntity);
    return new ResponseEntity<>("User updated successfully", HttpStatus.OK);
  }

  @Override
  public ResponseEntity<?> deleteUser(String userSeq) {

    try {
      userMapper.deleteUser(userSeq);
      return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>("delete user failed", HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @Override
  public ResponseEntity<List<UserDto>> getUserList(UserSearchDto userSearchDto) {

    List<UserEntity> userEntityList = userMapper.getUserList(userSearchDto);
    List<UserDto> userDtoList = userEntityList.stream()
        .map(userEntity -> UserDto.builder()
            .userSeq(userEntity.getUserSeq())
            .userId(userEntity.getUserId())
            .userEmail(userEntity.getUserEmail())
            .build())
        .toList();
    return new ResponseEntity<>(userDtoList, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<UserDto> getUserInfo(String userSeq) {

    UserEntity userEntity = userMapper.getUserBySeq(userSeq);
    UserDto userDto = UserDto.builder()
        .userSeq(userEntity.getUserSeq())
        .userId(userEntity.getUserId())
        .userEmail(userEntity.getUserEmail())
        .build();
    return new ResponseEntity<>(userDto, HttpStatus.OK);
  }

  private boolean checkPassword(UserDto userDto) {

    return userDto.getUserPassword().equals(userDto.getUserPasswordConfirm());
  }

  private boolean checkPassword(PasswordDto passwordDto) {

    return passwordDto.getNewPassword().equals(passwordDto.getNewPasswordConfirm());
  }
}

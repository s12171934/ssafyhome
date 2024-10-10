package com.ssafyhome.model.service.impl;

import com.ssafyhome.model.dao.mapper.UserMapper;
import com.ssafyhome.model.dto.UserDto;
import com.ssafyhome.model.dto.entity.mysql.UserEntity;
import com.ssafyhome.model.service.UserService;
import com.ssafyhome.util.SecretUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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

  private boolean checkPassword(UserDto userDto) {

    return userDto.getUserPassword().equals(userDto.getUserPasswordConfirm());
  }
}

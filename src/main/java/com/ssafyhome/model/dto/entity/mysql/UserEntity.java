package com.ssafyhome.model.dto.entity.mysql;

import lombok.Data;

@Data
public class UserEntity {

  private long userSeq;
  private String userId;
  private String userPw;
  private String userEmail;
  private String role = "ROLE_USER";

}

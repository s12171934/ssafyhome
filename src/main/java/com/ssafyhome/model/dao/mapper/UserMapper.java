package com.ssafyhome.model.dao.mapper;

import com.ssafyhome.model.dto.entity.mysql.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {

  UserEntity getUserBySeqAndEmail(@Param("userSeq") String seq, @Param("userEmail") String email);
  UserEntity getUserById(String id);
  void insertUser(UserEntity user) throws Exception;
  void updateUser(UserEntity user);
}

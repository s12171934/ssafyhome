package com.ssafyhome.model.dao.mapper;

import com.ssafyhome.model.dto.entity.mysql.UserEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

  UserEntity getUserBySeqAndEmail(String seq, String email);
}

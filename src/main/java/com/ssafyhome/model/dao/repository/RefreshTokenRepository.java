package com.ssafyhome.model.dao.repository;

import com.ssafyhome.model.dto.entity.redis.RefreshTokenEntity;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshTokenEntity, String> {
}

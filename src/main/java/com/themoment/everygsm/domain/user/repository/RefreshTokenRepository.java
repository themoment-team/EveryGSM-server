package com.themoment.everygsm.domain.user.repository;

import com.themoment.everygsm.domain.user.entity.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
}

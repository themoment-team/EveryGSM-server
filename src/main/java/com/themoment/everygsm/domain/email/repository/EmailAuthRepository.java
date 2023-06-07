package com.themoment.everygsm.domain.email.repository;

import com.themoment.everygsm.domain.email.entity.EmailAuth;
import org.springframework.data.repository.CrudRepository;

public interface EmailAuthRepository extends CrudRepository<EmailAuth, String> {
}

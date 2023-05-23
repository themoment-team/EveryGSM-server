package com.themoment.everygsm.domain.user.repository;

import com.themoment.everygsm.domain.user.entity.BlackList;
import org.springframework.data.repository.CrudRepository;

public interface BlackListRepository extends CrudRepository<BlackList, String> {
}

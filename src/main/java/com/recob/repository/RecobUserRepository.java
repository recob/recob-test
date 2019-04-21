package com.recob.repository;

import com.recob.domain.user.RecobUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecobUserRepository extends CrudRepository<RecobUser, String> {
}

package com.recob.map;

import com.recob.domain.answer.UserAnswer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends CrudRepository<UserAnswer, String> {
}

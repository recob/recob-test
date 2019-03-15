package com.recob.repository;

import com.recob.domain.answer.Answer;
import com.recob.domain.answer.AnswerId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, AnswerId> {

    List<Answer> findAllByUserId(String userId);

}

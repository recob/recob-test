package com.recob.service.answer;

/**
 * service is used to save answer from user
 */
public interface IAnswerService {

    /**
     * saving answer
     * @param questionId question id
     * @param answer answer id or answer if question has open type
     * @param userId user id
     */
    void saveAnswer(long questionId, String answer, String userId);
}

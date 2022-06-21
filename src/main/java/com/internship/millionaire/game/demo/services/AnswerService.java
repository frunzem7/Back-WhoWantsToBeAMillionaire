package com.internship.millionaire.game.demo.services;


import com.internship.millionaire.game.demo.dto.AnswerDto;
import org.springframework.http.HttpStatus;

import java.util.List;

public interface AnswerService {
    AnswerDto saveAnswer(AnswerDto answerDto) throws Exception;

    List<AnswerDto> getAll();

    AnswerDto getById(Long id) throws Exception;

    HttpStatus deleteById(Long id);

    AnswerDto update(AnswerDto answerDto);

    List<AnswerDto> getAllByQuestionId(Long id);
}

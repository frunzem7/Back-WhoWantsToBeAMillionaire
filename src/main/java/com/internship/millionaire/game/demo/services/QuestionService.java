package com.internship.millionaire.game.demo.services;

import com.internship.millionaire.game.demo.dto.QuestionDto;
import org.springframework.http.HttpStatus;

import java.util.List;

public interface QuestionService {
    QuestionDto saveQuestion(QuestionDto questionDto);

    List<QuestionDto> getAll();

    QuestionDto getById(Long id) throws Exception;

    HttpStatus deleteById(Long id);

    QuestionDto update(QuestionDto questionDto);
}

package com.internship.millionaire.game.demo.services.impl;

import com.internship.millionaire.game.demo.dto.QuestionDto;
import com.internship.millionaire.game.demo.entities.Question;
import com.internship.millionaire.game.demo.repositories.QuestionRepository;
import com.internship.millionaire.game.demo.services.QuestionService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;
    private final ModelMapper modelMapper;

    @Override
    public QuestionDto saveQuestion(QuestionDto questionDto) {
        Question savedQuestion = questionRepository.save(modelMapper.map(questionDto, Question.class));
        return modelMapper.map(savedQuestion, QuestionDto.class);
    }

    @Override
    public List<QuestionDto> getAll() {
        return questionRepository.findAll().stream()
                .map(question -> modelMapper.map(question, QuestionDto.class))
                .toList();
    }

    @Override
    public QuestionDto getById(Long id) throws Exception {
        return questionRepository.findById(id)
                .map(value -> modelMapper.map(value, QuestionDto.class))
                .orElseThrow(() -> new Exception("Question with id %s was not found".formatted(id)));
    }

    @Override
    public HttpStatus deleteById(Long id) {
        Optional<Question> question = questionRepository.findById(id);
        if (question.isPresent()) {
            questionRepository.deleteById(id);
            return HttpStatus.OK;
        }
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    public QuestionDto update(QuestionDto questionDto) {
        Optional<Question> optionalQuestion = questionRepository.findById(questionDto.getId());
        if (optionalQuestion.isPresent()) {
            Question question = optionalQuestion.get();
            question.setQuestion(questionDto.getQuestion());
            questionRepository.save(question);
            return modelMapper.map(question, QuestionDto.class);
        }
        return null;
    }
}

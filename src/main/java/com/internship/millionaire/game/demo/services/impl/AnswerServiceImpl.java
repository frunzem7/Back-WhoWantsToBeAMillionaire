package com.internship.millionaire.game.demo.services.impl;

import com.internship.millionaire.game.demo.dto.AnswerDto;
import com.internship.millionaire.game.demo.dto.QuestionDto;
import com.internship.millionaire.game.demo.entities.Answer;
import com.internship.millionaire.game.demo.entities.Question;
import com.internship.millionaire.game.demo.repositories.AnswerRepository;
import com.internship.millionaire.game.demo.repositories.QuestionRepository;
import com.internship.millionaire.game.demo.services.AnswerService;
import com.internship.millionaire.game.demo.services.QuestionService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnswerServiceImpl implements AnswerService {
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final ModelMapper modelMapper;
    private final QuestionService questionService;

    @Override
    public AnswerDto saveAnswer(AnswerDto answerDto) throws Exception {
        QuestionDto question = questionService.getById(answerDto.getQuestion().getId());
        Answer newAnswer = modelMapper.map(answerDto, Answer.class);
        newAnswer.setQuestions(modelMapper.map(question, Question.class));
        Answer savedAnswer = answerRepository.save(newAnswer);
        return modelMapper.map(savedAnswer, AnswerDto.class);
    }

    @Override
    public List<AnswerDto> getAll() {
        return answerRepository.findAll().stream()
                .map(answer -> modelMapper.map(answer, AnswerDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public AnswerDto getById(Long id) throws Exception {
        return answerRepository.findById(id)
                .map(value -> modelMapper.map(value, AnswerDto.class))
                .orElseThrow(() -> new Exception(String.format("Answer with id %s was not found", id)));
    }

    @Override
    public HttpStatus deleteById(Long id) {
        Optional<Answer> answer = answerRepository.findById(id);
        if (answer.isPresent()) {
            answerRepository.deleteById(id);
            return HttpStatus.OK;
        }
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    public AnswerDto update(AnswerDto answerDto) {
        Optional<Question> optionalQuestion = questionRepository.findById(answerDto.getQuestion().getId());
        if (optionalQuestion.isPresent()) {
            Question newQuestion = optionalQuestion.get();
            var t = newQuestion.getAnswers()
                    .stream()
                    .peek(s -> {
                        if (answerDto.getIsCorrect()) {
                            s.setIsCorrect(false);
                        }
                        if (s.getId().equals(answerDto.getId())) {
                            s.setAnswer(answerDto.getAnswer());
                            s.setIsCorrect(answerDto.getIsCorrect());
                        }
                    })
                    .toList();
            t.forEach(s -> answerRepository.save(s));
        }
        return null;
    }

    @Override
    public List<AnswerDto> getAllByQuestionId(Long id) {
        return answerRepository.findAllByQuestionsId(id).stream()
                .map(answer -> modelMapper.map(answer, AnswerDto.class))
                .collect(Collectors.toList());
    }
}

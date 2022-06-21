package com.internship.millionaire.game.demo.controllers;

import com.internship.millionaire.game.demo.dto.AnswerDto;
import com.internship.millionaire.game.demo.services.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/service/answers")
public class AnswerController {

    private final AnswerService answerService;

    @PostMapping("/")
    public AnswerDto saveAnswer(@RequestBody AnswerDto answerDto) throws Exception {
        return answerService.saveAnswer(answerDto);
    }

    @GetMapping("/")
    public List<AnswerDto> getAll() {
        return answerService.getAll();
    }

    @GetMapping("/{id}")
    public AnswerDto getById(@PathVariable Long id) throws Exception {
        return answerService.getById(id);
    }

    @DeleteMapping("/{id}")
    public HttpStatus deleteById(@PathVariable Long id) {
        return answerService.deleteById(id);
    }

    @PutMapping("/")
    public AnswerDto update(@RequestBody AnswerDto answerDto) {
        return answerService.update(answerDto);
    }

    @GetMapping("/all/question/{id}")
    public List<AnswerDto> getAllByQuestionId(@PathVariable Long id) {
        return answerService.getAllByQuestionId(id);
    }
}
package com.internship.millionaire.game.demo.controllers;

import com.internship.millionaire.game.demo.dto.QuestionDto;
import com.internship.millionaire.game.demo.services.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/service/questions")
public class QuestionController {
    private final QuestionService questionService;

    @PostMapping("/")
    public QuestionDto saveQuestion(@RequestBody QuestionDto questionDto) {
        return questionService.saveQuestion(questionDto);
    }

    @GetMapping("/")
    public List<QuestionDto> getAll() {
        return questionService.getAll();
    }

    @GetMapping("/{id}")
    public QuestionDto getById(@PathVariable Long id) throws Exception {
        return questionService.getById(id);
    }

    @DeleteMapping("/{id}")
    public HttpStatus deleteById(@PathVariable Long id) {
        return questionService.deleteById(id);
    }

    @PutMapping("/")
    public QuestionDto update(@RequestBody QuestionDto questionDto) throws Exception {
        return questionService.update(questionDto);
    }
}
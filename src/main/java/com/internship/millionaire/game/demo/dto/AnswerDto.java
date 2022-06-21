package com.internship.millionaire.game.demo.dto;

import lombok.Data;

@Data
public class AnswerDto {
    private Long id;
    private String answer;
    private Boolean isCorrect;
    private QuestionDto question;
}

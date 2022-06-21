package com.internship.millionaire.game.demo.entities;

import lombok.*;

import javax.persistence.*;

@Data
@Entity(name = "answer")
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "answer")
    private String answer;

    @Column(name = "isCorrect")
    private Boolean isCorrect = false;

    @ManyToOne(fetch = FetchType.LAZY)
    private Question questions;
}
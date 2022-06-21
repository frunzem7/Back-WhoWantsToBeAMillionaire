package com.internship.millionaire.game.demo.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Table(name = "question")
@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "question")
    private String question;

    @OneToMany(mappedBy = "questions", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Answer> answers;
}
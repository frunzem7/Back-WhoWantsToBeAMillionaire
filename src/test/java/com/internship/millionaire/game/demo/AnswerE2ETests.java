package com.internship.millionaire.game.demo;

import com.internship.millionaire.game.demo.dto.AnswerDto;
import com.internship.millionaire.game.demo.dto.QuestionDto;
import com.internship.millionaire.game.demo.services.QuestionService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.reactive.function.BodyInserters;

import javax.servlet.http.HttpServletRequest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureWebTestClient(timeout = "PT1M")//30 seconds
public class AnswerE2ETests {
    private String serverURL;

    @LocalServerPort
    private int port;

    private final WebTestClient webTestClient;

    @Mock
    private HttpServletRequest request;

    private final QuestionService service;

    @BeforeAll
    public void setUp() {
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        serverURL = String.format("%s:%s", "localhost", port);
    }

    @AfterEach
    public void clearEach() {
//        service.deleteAll();
    }

    @Test
    @DisplayName("Save a valid answer and return a valid saved dto.")
    public void saveAnswer() {
        QuestionDto questionDto = new QuestionDto();
        questionDto.setQuestion("aaaaaa");

        QuestionDto savedQuestion = service.saveQuestion(questionDto);

        //arrange
        AnswerDto createDto = new AnswerDto();
        createDto.setAnswer("Chisinau");
        createDto.setAnswer("Roma");
        createDto.setAnswer("Madrid");
        createDto.setAnswer("Bucharest");
        createDto.setQuestion(savedQuestion);

        //act
        AnswerDto savedAnswer = webTestClient
                .post()
                .uri(serverURL + "/api/service/answers/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(createDto))
                .exchange()
                .expectBody(AnswerDto.class)
                .returnResult()
                .getResponseBody();

        // assert
        Assertions.assertNotNull(savedAnswer);
        Assertions.assertEquals(createDto.getAnswer(), savedAnswer.getAnswer());

        this.webTestClient
                .delete()
                .uri(serverURL + "/api/service/answers/" + savedAnswer.getId())
                .exchange()
                .expectStatus().is2xxSuccessful();
        service.deleteById(savedQuestion.getId());
    }

    @ParameterizedTest
    @ValueSource(strings = {"Chisinau"})
    @DisplayName("Update a valid answer and return a valid updated dto")
    public void updateAnswer(String answer) {
        QuestionDto questionDto = new QuestionDto();
        questionDto.setQuestion("aaaaaa");

        QuestionDto updatedQuestion = service.saveQuestion(questionDto);

        //arrange
        AnswerDto createDto = new AnswerDto();
        createDto.setAnswer("Chisinau");
        createDto.setAnswer("Roma");
        createDto.setAnswer("Madrid");
        createDto.setAnswer("Bucharest");
        createDto.setQuestion(updatedQuestion);

        //act
        AnswerDto updatedAnswer = webTestClient
                .post()
                .uri(serverURL + "/api/service/answers/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(createDto))
                .exchange()
                .expectBody(AnswerDto.class)
                .returnResult()
                .getResponseBody();

        // assert
        Assertions.assertNotNull(updatedAnswer);
        Assertions.assertEquals(createDto.getAnswer(), updatedAnswer.getAnswer());

        this.webTestClient
                .delete()
                .uri(serverURL + "/api/service/answers/" + updatedAnswer.getId())
                .exchange()
                .expectStatus().is2xxSuccessful();
        service.deleteById(updatedQuestion.getId());
    }

    @Test
    @DisplayName("Delete answers")
    public void deleteAnswerById() {
        QuestionDto questionDto = new QuestionDto();
        questionDto.setQuestion("aaaaaa");

        QuestionDto deletedQuestion = service.saveQuestion(questionDto);

        //arrange
        AnswerDto createDto = new AnswerDto();
        createDto.setAnswer("Chisinau");
        createDto.setAnswer("Roma");
        createDto.setAnswer("Madrid");
        createDto.setAnswer("Bucharest");
        createDto.setQuestion(deletedQuestion);

        //act
        AnswerDto deletedAnswer = webTestClient
                .post()
                .uri(serverURL + "/api/service/answers/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(createDto))
                .exchange()
                .expectBody(AnswerDto.class)
                .returnResult()
                .getResponseBody();

        // assert
        Assertions.assertNotNull(deletedAnswer);
        Assertions.assertEquals(createDto.getAnswer(), deletedAnswer.getAnswer());

        this.webTestClient
                .delete()
                .uri(serverURL + "/api/service/answers/" + deletedAnswer.getId())
                .exchange()
                .expectStatus().is2xxSuccessful();
        service.deleteById(deletedQuestion.getId());
    }

    @Test
    @DisplayName("Get answer")
    public void getAnswer() {
        QuestionDto questionDto = new QuestionDto();
        questionDto.setQuestion("aaaaaa");

        QuestionDto getQuestion = service.saveQuestion(questionDto);

        //arrange
        AnswerDto createDto = new AnswerDto();
        createDto.setAnswer("Chisinau");
        createDto.setAnswer("Roma");
        createDto.setAnswer("Madrid");
        createDto.setAnswer("Bucharest");
        createDto.setQuestion(getQuestion);

        //act
        AnswerDto getAnswer = webTestClient
                .post()
                .uri(serverURL + "/api/service/answers/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(createDto))
                .exchange()
                .expectBody(AnswerDto.class)
                .returnResult()
                .getResponseBody();

        // assert
        Assertions.assertNotNull(getAnswer);
        Assertions.assertEquals(createDto.getAnswer(), getAnswer.getAnswer());

        this.webTestClient
                .delete()
                .uri(serverURL + "/api/service/answers/" + getAnswer.getId())
                .exchange()
                .expectStatus().is2xxSuccessful();
        service.deleteById(getQuestion.getId());
    }
}
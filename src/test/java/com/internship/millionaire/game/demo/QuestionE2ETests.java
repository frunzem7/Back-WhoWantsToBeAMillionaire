package com.internship.millionaire.game.demo;

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
public class QuestionE2ETests {
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
    @DisplayName("Save a valid question and return a valid saved dto.")
    public void saveQuestion() {
        //arrange
        QuestionDto createDto = new QuestionDto();
        createDto.setQuestion("What is the capital of the Republic of Moldova?");

        //act
        QuestionDto savedQuestion = webTestClient
                .post()
                .uri(serverURL + "/api/service/questions/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(createDto))
                .exchange()
                .expectBody(QuestionDto.class)
                .returnResult()
                .getResponseBody();

        // assert
        Assertions.assertNotNull(savedQuestion);
        Assertions.assertEquals(createDto.getQuestion(), savedQuestion.getQuestion());

        this.webTestClient
                .delete()
                .uri(serverURL + "/api/service/questions/" + savedQuestion.getId())
                .exchange()
                .expectStatus().is2xxSuccessful();
    }

    @ParameterizedTest
    @ValueSource(strings = {"What is the capital of the Republic of Moldova?"})
    @DisplayName("Update a valid question and return a valid updated dto")
    public void updateQuestion(String question) {
        //arrange
        QuestionDto createDto = new QuestionDto();
        createDto.setQuestion(question);

        QuestionDto savedQuestion = webTestClient
                .post().
                uri(serverURL + "/api/service/questions/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(createDto))
                .exchange()
                .expectBody(QuestionDto.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertNotNull(savedQuestion);

        savedQuestion.setQuestion(question);

        // act
        QuestionDto updatedDto = webTestClient
                .put()
                .uri(serverURL + "/api/service/questions/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(savedQuestion))
                .exchange()
                .expectBody(QuestionDto.class)
                .returnResult()
                .getResponseBody();

        //assert
        Assertions.assertNotNull(updatedDto);
        Assertions.assertEquals(savedQuestion, updatedDto);

        this.webTestClient
                .delete()
                .uri(serverURL + "/api/service/questions/" + savedQuestion.getId())
                .exchange()
                .expectStatus().is2xxSuccessful();
    }

    @Test
    @DisplayName("Delete questions")
    public void deleteQuestionById() {
        //arrange
        QuestionDto createDTO = new QuestionDto();
        createDTO.setQuestion("What is the capital of the Republic of Moldova?");

        QuestionDto savedQuestion = webTestClient
                .post()
                .uri(serverURL + "/api/service/questions/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(createDTO))
                .exchange()
                .expectBody(QuestionDto.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertNotNull(savedQuestion);

        //act
        this.webTestClient
                .delete()
                .uri(serverURL + "/api/service/questions/" + savedQuestion.getId())
                .exchange()
                .expectStatus().is2xxSuccessful();

    }

    @Test
    @DisplayName("Get question")
    public void getQuestion() {
        //arrange
        QuestionDto createDTO = new QuestionDto();
        createDTO.setQuestion("What is the capital of the Republic of Moldova?");

        QuestionDto savedQuestion = webTestClient
                .post()
                .uri(serverURL + "/api/service/questions/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(createDTO))
                .exchange()
                .expectBody(QuestionDto.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertNotNull(savedQuestion);

        //act
        QuestionDto gotQuestion = this.webTestClient
                .get()
                .uri(serverURL + "/api/service/questions/" + savedQuestion.getId())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(QuestionDto.class)
                .returnResult()
                .getResponseBody();

        // assertions
        Assertions.assertNotNull(gotQuestion);
        Assertions.assertEquals(createDTO.getQuestion(), gotQuestion.getQuestion());

        this.webTestClient
                .delete()
                .uri(serverURL + "/api/service/questions/" + savedQuestion.getId())
                .exchange()
                .expectStatus().is2xxSuccessful();
    }
}

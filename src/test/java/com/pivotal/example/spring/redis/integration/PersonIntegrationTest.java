package com.pivotal.example.spring.redis.integration;

import com.pivotal.example.spring.redis.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PersonIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    //@MockBean
    //private UUIDGenerator mockUUIDGenerator;

    //@Autowired
    //private MockMvc mockMvc;

    @BeforeEach
    public void init() {
        // MockitoAnnotations.initMocks(this);
    }

    @Test
    public void whenCheckingPersonExist_shouldReturnFalse() {
        webTestClient
                // Create a GET request to test an endpoint
                .get().uri("/persons/4")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                // and use the dedicated DSL to test assertions against the response
                .expectStatus().isOk()
                .expectBody(Boolean.class).isEqualTo(Boolean.FALSE);
    }

    // @Test
    public void whenCheckingPersonExist_shouldReturnTrue() {
        //Mockito.when(mockUUIDGenerator.generateRandomId()).thenReturn("1");
        webTestClient
                // Create a GET request to test an endpoint
                .get().uri("/persons/1")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                // and use the dedicated DSL to test assertions against the response
                .expectStatus().isOk()
                .expectBody(Boolean.class).isEqualTo(Boolean.TRUE);
    }

    @Test
    public void whenCheckingAllPersons_shouldReturnAllPersons() {
        webTestClient
                // Create a GET request to test an endpoint
                .get().uri("/persons")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                // and use the dedicated DSL to test assertions against the response
                .expectStatus().isOk()
                .expectBodyList(Person.class);
    }

    @Test
    public void whenAddNewPerson_shouldReturnNewId() {
        Person person = new Person("", "");
        //webTestClient.get().u
    }
}
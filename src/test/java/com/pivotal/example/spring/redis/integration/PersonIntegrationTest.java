package com.pivotal.example.spring.redis.integration;

import com.pivotal.example.spring.redis.model.Person;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureWebTestClient
public class PersonIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void whenCheckingIfPersonExist_shouldReturnFalse() {
        webTestClient
                // Create a GET request to test an endpoint
                .get().uri("/persons/contains/4")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                // and use the dedicated DSL to test assertions against the response
                .expectStatus().isOk()
                .expectBody(Boolean.class).isEqualTo(Boolean.FALSE);
    }

    @Test
    public void whenCheckingAllPersons_shouldReturnCorrectCount() {
        webTestClient
                // Create a GET request to test an endpoint
                .get().uri("/persons")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                // and use the dedicated DSL to test assertions against the response
                .expectStatus().isOk()
                .expectBodyList(Person.class)
                .consumeWith(result -> {
                    List<Person> persons = result.getResponseBody();
                    assertThat(persons.size() == 3);
                });
    }

    @Test
    public void whenCheckingPersonById_shouldReturnPerson() {
        List<String> ids = new ArrayList<>();
        webTestClient
                // Create a GET request to test an endpoint
                .get().uri("/persons")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                // and use the dedicated DSL to test assertions against the response
                .expectStatus().isOk()
                .expectBodyList(Person.class)
                .consumeWith(result -> result.getResponseBody().stream().forEach(person -> ids.add(person.getId())));

        webTestClient
                // Create a GET request to test an endpoint
                .get().uri("/persons/" + ids.get(0))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                // and use the dedicated DSL to test assertions against the response
                .expectStatus().isOk()
                .expectBody(Person.class)
                .consumeWith(result -> {
                    assertThat(StringUtils.equalsIgnoreCase(result.getResponseBody().getId(), ids.get(0)) == true);
                });
    }

    @Test
    public void addingPerson_shouldSucceed() {
        webTestClient
                // Create a GET request to test an endpoint
                .post().uri("/persons/add")
                .body(Mono.just(new Person("1", "test4", "test4@test.com")), Person.class)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk();
    }
}
package com.pivotal.example.spring.redis.service;

import com.pivotal.example.spring.redis.model.Person;
import com.pivotal.example.spring.redis.utils.UUIDGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class TestPersonService {

    @Mock
    private ReactiveRedisOperations<String, Person> mockPersonReactiveRedisOperations;
    @Mock
    private UUIDGenerator mockUUIDGenerator;
    private PersonService personService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        personService = new PersonService(mockPersonReactiveRedisOperations, mockUUIDGenerator);
    }

    @Test
    public void doesPersonExistByIdTest() {
        Mockito.when(mockPersonReactiveRedisOperations.hasKey("1")).thenReturn(Mono.empty());
        StepVerifier.create(personService.doesPersonExistById("1"))
                .expectNext(true)
                .expectComplete();
    }

    @Test
    public void testGetAllPersons() {
        Mockito.when(mockPersonReactiveRedisOperations.keys("*")).thenReturn(Flux.empty());
        StepVerifier.create(personService.getAllPersons())
                .expectComplete();
    }
}

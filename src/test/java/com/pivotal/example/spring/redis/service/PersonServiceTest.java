package com.pivotal.example.spring.redis.service;

import com.pivotal.example.spring.redis.model.Person;
import com.pivotal.example.spring.redis.utils.UUIDGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveValueOperations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class PersonServiceTest {

    @Mock
    private ReactiveRedisOperations<String, Person> mockPersonReactiveRedisOperations;

    @Mock
    private UUIDGenerator mockUUIDGenerator;

    @Mock
    private UUIDGenerator uuidGenerator;

    private PersonService personService;
    private ReactiveValueOperations<String, Person> valueOperations = Mockito.mock(ReactiveValueOperations.class);
    private Person person;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        personService = new PersonService(mockPersonReactiveRedisOperations, mockUUIDGenerator);
        Mockito.when(mockPersonReactiveRedisOperations.opsForValue()).thenReturn(valueOperations);
        person = new Person("test1", "test1@test.com");
    }

    @Test
    public void testPersonExistById() {
        Mockito.when(mockPersonReactiveRedisOperations.hasKey("1")).thenReturn(Mono.empty());
        StepVerifier.create(personService.doesPersonExistById("1"))
                .expectNext(true)
                .expectComplete();
    }

    @Test
    public void testGetAllPersons_Empty() {
        Mockito.when(mockPersonReactiveRedisOperations.keys("*")).thenReturn(Flux.empty());
        StepVerifier.create(personService.getAllPersons())
                .expectNextCount(0)
                .expectComplete();
    }

    @Test
    public void testGetAllPersons() {
        Mockito.when(mockPersonReactiveRedisOperations.keys("*")).thenReturn(Flux.just("1"));
        Mockito.when(mockPersonReactiveRedisOperations.opsForValue().get(person)).thenReturn(Mono.just(person));
        StepVerifier.create(personService.getAllPersons())
                .expectNextCount(1)
                .expectComplete();
    }

    @Test
    public void testGetPersonById() {
        Mockito.when(mockPersonReactiveRedisOperations.keys("*")).thenReturn(Flux.just("1"));
        Mockito.when(mockPersonReactiveRedisOperations.opsForValue().get("1")).thenReturn(Mono.just(person));
        StepVerifier.create(personService.getPersonById("1"))
                .expectNext(person)
                .expectNextCount(1)
                .expectComplete();
    }

    //@Test
    public void testAddPerson() {
        Mockito.when(uuidGenerator.generateRandomId()).thenReturn("1");
        Mockito.when(mockPersonReactiveRedisOperations.opsForValue().set("1", person)).thenReturn(Mono.just(Boolean.TRUE));
        StepVerifier.create(personService.addPerson(person))
                .expectNext("1")
                .expectComplete();
    }
}

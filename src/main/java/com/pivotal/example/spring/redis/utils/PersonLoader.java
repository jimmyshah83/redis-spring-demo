package com.pivotal.example.spring.redis.utils;

import com.pivotal.example.spring.redis.model.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Component
public class PersonLoader {

    private final ReactiveRedisConnectionFactory reactiveRedisConnectionFactory;
    private final ReactiveRedisOperations<String, Person> personReactiveRedisOperations;
    private final UUIDGenerator uuidGenerator;

    @PostConstruct
    public void loadData() {
        List<Person> persons = Arrays.asList(new Person(uuidGenerator.generateRandomId(), "Person_1", "person1@test.com"),
                new Person(uuidGenerator.generateRandomId(), "Person_2", "person2@test.com"),
                new Person(uuidGenerator.generateRandomId(), "Person_3", "person3@test.com"));

        reactiveRedisConnectionFactory.getReactiveConnection().serverCommands().flushAll().thenMany(
                Flux.fromStream(persons.stream())
                        .flatMap(person -> personReactiveRedisOperations.opsForValue().set(person.getId(), person)))
                .thenMany(personReactiveRedisOperations.keys("*")
                        .flatMap(personReactiveRedisOperations.opsForValue()::get))
                .subscribe(System.out::println);
    }
}

package com.pivotal.example.spring.redis.controller;

import com.pivotal.example.spring.redis.model.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@RestController
public class PersonController {

    private final ReactiveRedisOperations<String, Person> personReactiveRedisOperations;

    @GetMapping("/persons")
    public Flux<Person> getAll() {
        return personReactiveRedisOperations.keys("*")
                .flatMap(personReactiveRedisOperations.opsForValue()::get);
    }
}

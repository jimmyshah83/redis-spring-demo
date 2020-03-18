package com.pivotal.example.spring.redis.service;

import com.pivotal.example.spring.redis.model.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class PersonService {

    private final ReactiveRedisOperations<String, Person> personReactiveRedisOperations;

    public Flux<Person> getAllPersons() {
        return personReactiveRedisOperations.keys("*")
                .flatMap(person -> personReactiveRedisOperations.opsForValue().get(person));
    }

    public Mono<Boolean> doesPersonExistById(String id) {
        return personReactiveRedisOperations.hasKey(id);
    }
}
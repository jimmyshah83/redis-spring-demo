package com.pivotal.example.spring.redis.utils;

import com.pivotal.example.spring.redis.model.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import javax.annotation.PostConstruct;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class PersonLoader {

    private final ReactiveRedisConnectionFactory factory;
    private final ReactiveRedisOperations<String, Person> personOps;

    @PostConstruct
    public void loadData() {
        factory.getReactiveConnection().serverCommands().flushAll().thenMany(
                Flux.just("Person_1", "Person_1", "Person_1")
                        .map(name -> new Person(UUID.randomUUID().toString(), name, "test@test.com"))
                        .flatMap(person -> personOps.opsForValue().set(person.getId(), person)))
                .thenMany(personOps.keys("*")
                        .flatMap(personOps.opsForValue()::get))
                .subscribe(System.out::println);
    }
}

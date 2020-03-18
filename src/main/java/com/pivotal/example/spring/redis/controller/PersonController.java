package com.pivotal.example.spring.redis.controller;

import com.pivotal.example.spring.redis.model.Person;
import com.pivotal.example.spring.redis.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
public class PersonController {

    private final PersonService personService;

    @GetMapping("/persons")
    public Flux<Person> getAllPersons() {
        return personService.getAllPersons();
    }

    @GetMapping("/persons/{id}")
    public Mono<Boolean> doesPersonExistById(@PathVariable String id) {
        return personService.doesPersonExistById(id);
    }
}

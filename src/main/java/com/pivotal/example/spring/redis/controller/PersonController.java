package com.pivotal.example.spring.redis.controller;

import com.pivotal.example.spring.redis.model.Person;
import com.pivotal.example.spring.redis.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/persons/contains/{id}")
    public Mono<Boolean> doesPersonExistById(@PathVariable String id) {
        return personService.doesPersonExistById(id);
    }

    @PostMapping(value = "/persons/add")
    public Mono<Boolean> addPerson(@RequestBody Person person) {
        return personService.addPerson(person);
    }

    @GetMapping("/persons/{id}")
    public Mono<Person> findPersonById(@PathVariable String id) {
        return personService.getPersonById(id);
    }
}

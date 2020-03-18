package com.pivotal.example.spring.redis.repository;

import com.pivotal.example.spring.redis.model.Person;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface PersonRepository extends ReactiveCrudRepository<Person, String> {
}

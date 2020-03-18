package com.pivotal.example.spring.redis.utils;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UUIDGenerator {

    public String generateRandomId() {
        return UUID.randomUUID().toString();
    }
}

package com.example.demospringboot.utils;

import org.springframework.stereotype.Component;

import java.util.UUID;
@Component
public class UUIDGenerator implements IRandomStringGenerator{
    @Override
    public String random() {
        return UUID.randomUUID().toString();
    }
}

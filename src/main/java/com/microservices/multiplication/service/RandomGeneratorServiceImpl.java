package com.microservices.multiplication.service;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class RandomGeneratorServiceImpl implements RandomGeneratorService {

    final static int MINIMUM_FACTOR = 11;
    final static int MAXIMUM_FACTOR = 99;

    @Override
    public int generatedRandomFactor() {
        return new Random().nextInt((MAXIMUM_FACTOR - MINIMUM_FACTOR) + 1) + MINIMUM_FACTOR;
    }
}

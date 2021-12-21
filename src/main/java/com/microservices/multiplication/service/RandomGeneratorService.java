package com.microservices.multiplication.service;

public interface RandomGeneratorService {
    /**
     * @return a randomly generated factor.
     * Always between 11 and 99
     */
    int generatedRandomFactor();
}

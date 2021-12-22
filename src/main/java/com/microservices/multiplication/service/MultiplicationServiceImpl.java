package com.microservices.multiplication.service;

import com.microservices.multiplication.domain.Multiplication;
import org.springframework.stereotype.Service;

@Service
public class MultiplicationServiceImpl implements MultiplicationService{

    private RandomGeneratorService randomGeneratorService;

    public MultiplicationServiceImpl(RandomGeneratorService randomGeneratorService) {
        this.randomGeneratorService = randomGeneratorService;
    }
    @Override
    public Multiplication createRandomMultiplication() {
        int factorA = randomGeneratorService.generatedRandomFactor();
        int factorB = randomGeneratorService.generatedRandomFactor();

        return new Multiplication(factorA, factorB);
    }
}

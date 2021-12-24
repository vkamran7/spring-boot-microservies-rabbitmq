package com.microservices.multiplication.service;

import com.microservices.multiplication.domain.Multiplication;
import com.microservices.multiplication.domain.MultiplicationResultAttempt;
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

    @Override
    public boolean checkAttempt(final MultiplicationResultAttempt resultAttempt) {
        return resultAttempt.getResultAttempt() ==
                resultAttempt.getMultiplication().getFactorA() * createRandomMultiplication().getFactorB();
    }
}

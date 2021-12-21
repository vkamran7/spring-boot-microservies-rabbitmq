package com.microservices.multiplication.service;

import com.microservices.multiplication.domain.Multiplication;

public interface MultiplicationService {
    /**
     * Created a multiplication object with two
     * randomly generated factors
     * between 11 and 99
     *
     * @return a Multiplication object with random factors
     */
    Multiplication createRandomMultiplication();
}

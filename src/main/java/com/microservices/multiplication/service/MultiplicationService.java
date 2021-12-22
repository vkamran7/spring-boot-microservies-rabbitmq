package com.microservices.multiplication.service;

import com.microservices.multiplication.domain.Multiplication;
import com.microservices.multiplication.domain.MultiplicationResultAttempt;

public interface MultiplicationService {

    /**
     * Generates a random {@link Multiplication} object
     * @return a multiplication of randomly generated numbers
     */
    Multiplication createRandomMultiplication();

    /**
     *
     * @param multiplicationResultAttempt
     * @return true if the attempt matches the result of the multiplication,
     * otherwise false
     */
    boolean checkAttempt(final MultiplicationResultAttempt multiplicationResultAttempt);
}

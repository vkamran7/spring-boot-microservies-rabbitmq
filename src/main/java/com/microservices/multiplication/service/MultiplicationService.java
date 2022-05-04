package com.microservices.multiplication.service;

import com.microservices.multiplication.domain.Multiplication;
import com.microservices.multiplication.domain.MultiplicationResultAttempt;

import java.util.List;
import java.util.Optional;

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

    List<MultiplicationResultAttempt> getStatsForUser(String userAlias);

    MultiplicationResultAttempt getById(Long id);
}

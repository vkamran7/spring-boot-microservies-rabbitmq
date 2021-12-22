package com.microservices.multiplication.service;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

public class RandomGeneratorServiceImplTest {

    private RandomGeneratorServiceImpl randomGeneratorService;

    @Before
    public void setup() {
        randomGeneratorService = new RandomGeneratorServiceImpl();
    }

    @Test
    public void generateRandomFactorIsBetweenExpectedLimits() {
        List<Integer> randomFactors = IntStream.range(0, 1000)
                .map(i -> randomGeneratorService.generatedRandomFactor())
                .boxed()
                .collect(Collectors.toList());
        assertThat(randomFactors).isSubsetOf(IntStream.range(11, 100).boxed().collect(Collectors.toList()));
    }
}

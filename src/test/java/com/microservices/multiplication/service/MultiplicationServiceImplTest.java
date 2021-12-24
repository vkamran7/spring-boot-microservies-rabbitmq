package com.microservices.multiplication.service;

import com.microservices.multiplication.domain.Multiplication;
import com.microservices.multiplication.domain.MultiplicationResultAttempt;
import com.microservices.multiplication.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class MultiplicationServiceImplTest {

    private MultiplicationServiceImpl multiplicationService;

    @Mock
    private RandomGeneratorService randomGeneratorService;

    @Before
    public void setup() {
        multiplicationService = new MultiplicationServiceImpl(randomGeneratorService);
    }

    @Test
    public void createRandomMultiplicationTest() {
        given(randomGeneratorService.generatedRandomFactor())
                .willReturn(50, 30);
        Multiplication multiplication = multiplicationService.createRandomMultiplication();

        assertThat(multiplication.getFactorA()).isEqualTo(50);
        assertThat(multiplication.getFactorB()).isEqualTo(30);
//        assertThat(multiplication.getResult()).isEqualTo(1500);
    }

    @Test
    public void checkCorrectAttemptTest() {
        // given
        Multiplication multiplication = new Multiplication(50, 60);
        User user = new User("kamran");
        MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(user, multiplication, 3000);

        // when
        boolean attemptResult = multiplicationService.checkAttempt(attempt);

        // assert
        assertThat(attemptResult).isTrue();
    }

    @Test
    public void checkWrongAttemptTest() {
        // given
        Multiplication multiplication = new Multiplication(50, 60);
        User user = new User("kamran");
        MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(user, multiplication, 3010);

        // when
        boolean attemptResult = multiplicationService.checkAttempt(attempt);
        assertThat(attemptResult).isFalse();
    }
}

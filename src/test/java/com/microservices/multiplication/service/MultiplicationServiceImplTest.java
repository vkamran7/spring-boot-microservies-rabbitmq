package com.microservices.multiplication.service;

import com.microservices.multiplication.domain.Multiplication;
import com.microservices.multiplication.domain.MultiplicationResultAttempt;
import com.microservices.multiplication.domain.User;
import com.microservices.multiplication.repository.MultiplicationRepository;
import com.microservices.multiplication.repository.MultiplicationResultAttemptRepository;
import com.microservices.multiplication.repository.UserRepository;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class MultiplicationServiceImplTest {

    private MultiplicationServiceImpl multiplicationService;

    @Mock
    private RandomGeneratorService randomGeneratorService;

    @Mock
    private MultiplicationResultAttemptRepository attemptRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private MultiplicationRepository multiplicationRepository;

    @Before
    public void setup() {
        multiplicationService = new MultiplicationServiceImpl(randomGeneratorService, attemptRepository, userRepository, multiplicationRepository);
    }

    @Test
    public void createRandomMultiplicationTest() {
        given(randomGeneratorService.generatedRandomFactor())
                .willReturn(50, 30);
        Multiplication multiplication = multiplicationService.createRandomMultiplication();

        assertThat(multiplication.getFactorA()).isEqualTo(50);
        assertThat(multiplication.getFactorB()).isEqualTo(30);
    }

    @Test
    public void checkCorrectAttemptTest() {
        // given
        Multiplication multiplication = new Multiplication(50, 60);
        User user = new User("kamran");
        MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(user, multiplication, 3000, false);

        MultiplicationResultAttempt verifiedAttempt = new MultiplicationResultAttempt(user, multiplication, 3000, true);
        given(userRepository.findByAlias("kamran")).willReturn(Optional.empty());

        // when
        boolean attemptResult = multiplicationService.checkAttempt(attempt);

        // assert
        assertThat(attemptResult).isTrue();
        verify(attemptRepository).save(verifiedAttempt);
    }

    @Test
    public void checkWrongAttemptTest() {
        // given
        Multiplication multiplication = new Multiplication(50, 60);
        User user = new User("kamran");
        MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(user, multiplication, 3010, false);
        given(userRepository.findByAlias("kamran")).willReturn(Optional.empty());

        // when
        boolean attemptResult = multiplicationService.checkAttempt(attempt);
        assertThat(attemptResult).isFalse();
        verify(attemptRepository).save(attempt);
    }

    @Test
    public void retrieveStatsTest() {
        Multiplication multiplication = new Multiplication(50, 60);
        User user = new User("Kamran");
        MultiplicationResultAttempt attempt1 = new MultiplicationResultAttempt(
                user, multiplication, 3010, false
        );
        MultiplicationResultAttempt attempt2 = new MultiplicationResultAttempt(
                user, multiplication, 3051, false
        );
        List<MultiplicationResultAttempt> latestAttempts = Lists.newArrayList(attempt1, attempt2);
        given(userRepository.findByAlias("Kamran")).willReturn(Optional.empty());
        given(attemptRepository.findTop5ByUserAliasOrderByIdDesc("Kamran")).willReturn(latestAttempts);

        List<MultiplicationResultAttempt> latestAttemptResult = multiplicationService.getStatsForUser("Kamran");
        assertThat(latestAttemptResult).isEqualTo(latestAttempts);
    }
}

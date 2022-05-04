package com.microservices.multiplication.service;

import com.microservices.multiplication.domain.Multiplication;
import com.microservices.multiplication.domain.MultiplicationResultAttempt;
import com.microservices.multiplication.domain.User;
import com.microservices.multiplication.event.EventDispatcher;
import com.microservices.multiplication.event.MultiplicationSolvedEvent;
import com.microservices.multiplication.exception.AttemptNotFoundException;
import com.microservices.multiplication.repository.MultiplicationRepository;
import com.microservices.multiplication.repository.MultiplicationResultAttemptRepository;
import com.microservices.multiplication.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class MultiplicationServiceImpl implements MultiplicationService{

    private RandomGeneratorService randomGeneratorService;
    private MultiplicationResultAttemptRepository attemptRepository;
    private UserRepository userRepository;
    private MultiplicationRepository multiplicationRepository;
    private EventDispatcher eventDispatcher;

    public MultiplicationServiceImpl(final RandomGeneratorService randomGeneratorService,
                                     final MultiplicationResultAttemptRepository attemptRepository,
                                     final UserRepository userRepository,
                                     final MultiplicationRepository multiplicationRepository,
                                     final EventDispatcher eventDispatcher) {
        this.randomGeneratorService = randomGeneratorService;
        this.attemptRepository = attemptRepository;
        this.userRepository = userRepository;
        this.multiplicationRepository = multiplicationRepository;
        this.eventDispatcher = eventDispatcher;
    }

    @Override
    public Multiplication createRandomMultiplication() {
        int factorA = randomGeneratorService.generatedRandomFactor();
        int factorB = randomGeneratorService.generatedRandomFactor();

        return new Multiplication(factorA, factorB);
    }

    @Transactional
    @Override
    public boolean checkAttempt(final MultiplicationResultAttempt attempt) {
        // check if user already exists
        Optional<User> user = userRepository.findByAlias(attempt.getUser().getAlias());

        Optional<Multiplication> multiplication = multiplicationRepository.findByFactorAAndFactorB(attempt.getMultiplication().getFactorA(), attempt.getMultiplication().getFactorB());

        Assert.isTrue(!attempt.isCorrect(), "You can't send an attempt marked as correct");

        boolean isCorrect = attempt.getResultAttempt() ==
                attempt.getMultiplication().getFactorA() * attempt.getMultiplication().getFactorB();

        MultiplicationResultAttempt checkedAttempt = new MultiplicationResultAttempt(
                user.orElse(attempt.getUser()),
                multiplication.orElse(attempt.getMultiplication()),
                attempt.getResultAttempt(),
                isCorrect
        );

        // store the attempt
        attemptRepository.save(checkedAttempt);

        eventDispatcher.send(
                new MultiplicationSolvedEvent(checkedAttempt.getId(),
                        checkedAttempt.getUser().getId(),
                        checkedAttempt.isCorrect()));

        return isCorrect;
    }

    @Override
    public List<MultiplicationResultAttempt> getStatsForUser(String userAlias) {
        return attemptRepository.findTop5ByUserAliasOrderByIdDesc(userAlias);
    }


    @Override
    public MultiplicationResultAttempt getById(Long id) {
        Optional<MultiplicationResultAttempt> attempt = attemptRepository.findById(id);
        if (!attempt.isPresent()) {
            throw new AttemptNotFoundException("Not Found");
        }
        return attempt.get();
    }
}

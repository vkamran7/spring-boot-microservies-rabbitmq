package com.microservices.multiplication.repository;

import com.microservices.multiplication.domain.Multiplication;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface MultiplicationRepository extends CrudRepository<Multiplication, Long> {
    Optional<Multiplication> findByFactorAAndFactorB(int factorA, int factorB);
}

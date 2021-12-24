package com.microservices.multiplication.controller;

import com.microservices.multiplication.domain.Multiplication;
import com.microservices.multiplication.service.MultiplicationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/multiplications")
public final class MultiplicationController {

    private final MultiplicationService multiplicationService;

    MultiplicationController(final MultiplicationService multiplicationService) {
        this.multiplicationService = multiplicationService;
    }

    @GetMapping("/random")
    Multiplication getMultiplication() {
        return multiplicationService.createRandomMultiplication();
    }
}

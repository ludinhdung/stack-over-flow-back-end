package com.stackoverflowbackend.exceptions;

public class QuestionNotFoundException extends RuntimeException {

    public QuestionNotFoundException(Long id) {
        super(String.format("The question with id %d was not not found", id));
    }
}

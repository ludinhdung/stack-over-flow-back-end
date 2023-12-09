package com.stackoverflowbackend.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super(String.format("The user with id %d was not not found", id));
    }
}

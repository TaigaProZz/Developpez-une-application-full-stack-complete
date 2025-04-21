package com.openclassrooms.mddapi.errors;

public class UserAlreadySubscribedException extends RuntimeException {
    public UserAlreadySubscribedException(String message) {
        super(message);
    }
}

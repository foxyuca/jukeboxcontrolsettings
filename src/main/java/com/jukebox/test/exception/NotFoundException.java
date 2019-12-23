package com.jukebox.test.exception;

public class NotFoundException extends RuntimeException{

    public NotFoundException(final String message) {
        super(message);
    }

}

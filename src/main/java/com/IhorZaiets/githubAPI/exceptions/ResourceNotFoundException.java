package com.IhorZaiets.githubAPI.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException() {
        super(ExceptionMessage.RESOURCE_NOT_FOUND);
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }
}

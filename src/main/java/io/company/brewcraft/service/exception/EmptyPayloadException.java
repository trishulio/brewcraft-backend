package io.company.brewcraft.service.exception;

public class EmptyPayloadException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public EmptyPayloadException() {
        super("Empty payload received");
    }
}
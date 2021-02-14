package io.company.brewcraft.service.exception;

public class MaterialCategoryException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public MaterialCategoryException(String message) {
        super(message);
    }
}
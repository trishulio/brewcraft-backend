package io.company.brewcraft.service.exception;

public class IncompatibleQuantityUnitException extends IllegalArgumentException {
    private static final long serialVersionUID = 5114542775018005333L;

    public IncompatibleQuantityUnitException() {
        super();
    }

    public IncompatibleQuantityUnitException(String message) {
        super(message);
    }
}

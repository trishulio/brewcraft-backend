package io.company.brewcraft.service.exception;

import java.text.MessageFormat;

public class EntityNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public EntityNotFoundException(String entity, String entityId) {
        this(entity, "id", entityId);
    }

    public EntityNotFoundException(String entity, Object entityId) {
        this(entity, entityId.toString());
    }

    public EntityNotFoundException(String entity, String field, String value) {
        super(MessageFormat.format("{0} not found with {1}: {2}", entity, field, value));
    }
}
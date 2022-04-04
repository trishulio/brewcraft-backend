package io.company.brewcraft.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.company.brewcraft.dto.user.UserDto;
import io.company.brewcraft.model.EntityDecorator;
import io.company.brewcraft.model.TemporaryImageSrcDecorator;

public class UserDtoDecorator implements EntityDecorator<UserDto> {

    private static final Logger logger = LoggerFactory.getLogger(UserDtoDecorator.class);

    private TemporaryImageSrcDecorator imageSrcDecorator;

    public UserDtoDecorator(TemporaryImageSrcDecorator imageSrcDecorator) {
        this.imageSrcDecorator = imageSrcDecorator;
    }

    @Override
    public <R extends UserDto> void decorate(List<R> entities) {
        // Catching the exception so that the request doesn't fail at the controller level.
        // The service call will have completed at this point so the operation would have.
        // This is a temporary hack. Need ideas on where decorating the entity would be ideal.
        try {
            this.imageSrcDecorator.decorate(entities);
        } catch (Exception e) {
            logger.error("Failed to decorate the user DTO entities.");
        }
    }
}

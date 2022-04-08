package io.company.brewcraft.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.company.brewcraft.dto.user.UserDto;
import io.company.brewcraft.model.EntityDecorator;
import io.company.brewcraft.service.TemporaryImageSrcDecorator;

public class UserDtoDecorator implements EntityDecorator<UserDto> {
    private static final Logger logger = LoggerFactory.getLogger(UserDtoDecorator.class);

    private TemporaryImageSrcDecorator imageSrcDecorator;

    public UserDtoDecorator(TemporaryImageSrcDecorator imageSrcDecorator) {
        this.imageSrcDecorator = imageSrcDecorator;
    }

    @Override
    public <R extends UserDto> void decorate(List<R> entities) {
        this.imageSrcDecorator.decorate(entities);
    }
}

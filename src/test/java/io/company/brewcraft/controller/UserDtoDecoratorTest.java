package io.company.brewcraft.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

import java.net.URI;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.dto.IaasObjectStoreFileDto;
import io.company.brewcraft.dto.user.UserDto;
import io.company.brewcraft.model.EntityDecorator;
import io.company.brewcraft.service.TemporaryImageSrcDecorator;

public class UserDtoDecoratorTest {
    private EntityDecorator<UserDto> decorator;

    private TemporaryImageSrcDecorator mImgDecorator;

    @BeforeEach
    public void init() {
        mImgDecorator = mock(TemporaryImageSrcDecorator.class);

        decorator = new UserDtoDecorator(mImgDecorator);
    }

    @Test
    public void testDecorate_CallsImageDecoratorOnEntities() {
        doAnswer(inv -> {
            List<UserDto> dtos = inv.getArgument(0, List.class);
            dtos.forEach(dto -> dto.setObjectStoreFile(new IaasObjectStoreFileDto(URI.create("URI:" + dto.getId()))));
            return null;
        }).when(mImgDecorator).decorate(anyList());

        List<UserDto> dtos = List.of(new UserDto(1L), new UserDto(2L));
        decorator.decorate(dtos);

        List<UserDto> expected = List.of(new UserDto(1L), new UserDto(2L));
        expected.get(0).setObjectStoreFile(new IaasObjectStoreFileDto(URI.create("URI:1")));
        expected.get(1).setObjectStoreFile(new IaasObjectStoreFileDto(URI.create("URI:2")));
        assertEquals(expected, dtos);
    }
}

package io.company.brewcraft.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

import java.net.URI;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.dto.IaasObjectStoreFileDto;
import io.company.brewcraft.dto.ProductDto;
import io.company.brewcraft.model.EntityDecorator;
import io.company.brewcraft.model.TemporaryImageSrcDecorator;

public class ProductDtoDecoratorTest {
    private EntityDecorator<ProductDto> decorator;

    private TemporaryImageSrcDecorator mImgDecorator;

    @BeforeEach
    public void init() {
        mImgDecorator = mock(TemporaryImageSrcDecorator.class);

        decorator = new ProductDtoDecorator(mImgDecorator);
    }

    @Test
    public void testCallsImageDecoratorOnEntities() {
        doAnswer(inv -> {
            List<ProductDto> dtos = inv.getArgument(0, List.class);
            dtos.forEach(dto -> dto.setObjectStoreFile(new IaasObjectStoreFileDto(URI.create("URI:" + dto.getId()))));
            return null;
        }).when(mImgDecorator).decorate(anyList());

        List<ProductDto> dtos = List.of(new ProductDto(1L), new ProductDto(2L));
        decorator.decorate(dtos);

        List<ProductDto> expected = List.of(new ProductDto(1L), new ProductDto(2L));
        expected.get(0).setObjectStoreFile(new IaasObjectStoreFileDto(URI.create("URI:1")));
        expected.get(1).setObjectStoreFile(new IaasObjectStoreFileDto(URI.create("URI:2")));
        assertEquals(expected, dtos);
    }
}

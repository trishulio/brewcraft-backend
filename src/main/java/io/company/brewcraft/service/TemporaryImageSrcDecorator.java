package io.company.brewcraft.service;

import java.net.URI;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import io.company.brewcraft.controller.IaasObjectStoreFileController;
import io.company.brewcraft.dto.IaasObjectStoreFileDto;
import io.company.brewcraft.model.DecoratedIaasObjectStoreFileAccessor;
import io.company.brewcraft.model.EntityDecorator;

public class TemporaryImageSrcDecorator implements EntityDecorator<DecoratedIaasObjectStoreFileAccessor> {
    private IaasObjectStoreFileController objectStoreController;

    public TemporaryImageSrcDecorator(IaasObjectStoreFileController objectStoreController) {
        this.objectStoreController = objectStoreController;
    }

    @Override
    public <R extends DecoratedIaasObjectStoreFileAccessor> void decorate(List<R> entities) {
        Set<URI> uris = entities.stream()
                                .filter(Objects::nonNull)
                                .map(DecoratedIaasObjectStoreFileAccessor::getImageSrc)
                                .filter(Objects::nonNull)
                                .collect(Collectors.toSet());

        Iterator<IaasObjectStoreFileDto> files = objectStoreController.getAll(uris).iterator();

        entities.stream()
                .filter(entity -> entity != null && entity.getImageSrc() != null)
                .forEach(entity -> entity.setObjectStoreFile(files.next()));
    }
}

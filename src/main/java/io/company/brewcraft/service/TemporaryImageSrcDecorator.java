package io.company.brewcraft.service;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.company.brewcraft.controller.IaasObjectStoreFileController;
import io.company.brewcraft.dto.IaasObjectStoreFileDto;
import io.company.brewcraft.model.DecoratedIaasObjectStoreFileAccessor;
import io.company.brewcraft.model.EntityDecorator;

public class TemporaryImageSrcDecorator implements EntityDecorator<DecoratedIaasObjectStoreFileAccessor> {
    private static final Logger log = LoggerFactory.getLogger(TemporaryImageSrcDecorator.class);

    private IaasObjectStoreFileController objectStoreController;

    public TemporaryImageSrcDecorator(IaasObjectStoreFileController objectStoreController) {
        this.objectStoreController = objectStoreController;
    }

    @Override
    public <R extends DecoratedIaasObjectStoreFileAccessor> void decorate(List<R> entities) {
        // Catching the exception so that the request doesn't fail at the controller level.
        // The service call will have completed at this point so the operation would have been committed.
        // This is a temporary hack. Need ideas on where decorating the entity would be ideal.
        try {
            Map<URI, R> uriToEntity = entities.stream()
                    .filter(Objects::nonNull)
                    .filter(entity -> Objects.nonNull(entity.getImageSrc()))
                    .collect(Collectors.toMap(entity -> entity.getImageSrc(), Function.identity()));

            List<IaasObjectStoreFileDto> files = objectStoreController.getAll(uriToEntity.keySet());

            files.stream()
                 .forEach(file -> {
                     R entity = uriToEntity.get(file.getFileKey());
                     entity.setObjectStoreFile(file);
                 });
        } catch (Exception e) {
            log.error("Failed to decorate Dtos: {}", e);
        }
    }
}

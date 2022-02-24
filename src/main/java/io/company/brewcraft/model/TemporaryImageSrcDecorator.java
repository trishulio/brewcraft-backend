package io.company.brewcraft.model;

import java.net.URI;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import io.company.brewcraft.service.IaasObjectStoreFileSystem;

public class TemporaryImageSrcDecorator implements EntityDecorator<DecoratedImageUrlAccessor> {
    private IaasObjectStoreFileSystem fileSystem;
    private long expiraryDurationInHours;

    public TemporaryImageSrcDecorator(IaasObjectStoreFileSystem fileSystem, long expiraryDurationInHours) {
        this.fileSystem = fileSystem;
        this.expiraryDurationInHours = expiraryDurationInHours;
    }

    @Override
    public <R extends DecoratedImageUrlAccessor> void decorate(List<R> entities) {
        List<URI> urls = entities.stream()
                                .filter(Objects::nonNull)
                                .map(DecoratedImageUrlAccessor::getImageSrc)
                                .filter(Objects::nonNull)
                                .toList();

        LocalDateTime expiration = LocalDateTime.now().plusHours(expiraryDurationInHours);

        Iterator<URL> tempUrls = this.fileSystem.getTemporaryPublicFileDownloadPath(urls, expiration).iterator();

        entities.stream()
                .filter(entity -> entity != null && entity.getImageSrc() != null)
                .forEach(entity -> entity.setImageUrl(tempUrls.next()));
    }
}

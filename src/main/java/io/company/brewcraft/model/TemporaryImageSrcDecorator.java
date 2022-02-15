package io.company.brewcraft.model;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import io.company.brewcraft.service.IaasObjectStoreFileSystem;

public class TemporaryImageSrcDecorator implements EntityDecorator<ImageSrcAccessor> {
    private IaasObjectStoreFileSystem fileSystem;
    private long expiraryDurationInHours;

    public TemporaryImageSrcDecorator(IaasObjectStoreFileSystem fileSystem, long expiraryDurationInHours) {
        this.fileSystem = fileSystem;
        this.expiraryDurationInHours = expiraryDurationInHours;
    }

    @Override
    public <R extends ImageSrcAccessor> void decorate(List<R> entities) {
        List<URI> urls = entities.stream()
                                .filter(Objects::nonNull)
                                .map(ImageSrcAccessor::getImageSrc)
                                .filter(Objects::nonNull)
                                .toList();

        LocalDateTime expiration = LocalDateTime.now().plusHours(expiraryDurationInHours);

        Iterator<URI> tempUris = this.fileSystem.getTemporaryPublicFileDownloadPath(urls, expiration)
                                 .stream()
                                 .map(url -> {
                                    try {
                                        return url.toURI();
                                    } catch (URISyntaxException e) {
                                        throw new RuntimeException(e);
                                    }
                                })
                                 .iterator();

        entities.stream()
                .filter(entity -> entity != null && entity.getImageSrc() != null)
                .forEach(entity -> entity.setImageSrc(tempUris.next()));
    }

}

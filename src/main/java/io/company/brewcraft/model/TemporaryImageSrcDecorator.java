package io.company.brewcraft.model;

import java.net.URL;
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
        List<URL> urls = entities.stream()
                                .filter(Objects::nonNull)
                                .map(ImageSrcAccessor::getImageSrc)
                                .toList();
        
        LocalDateTime expiration = LocalDateTime.now().plusHours(expiraryDurationInHours);
        
        Iterator<URL> tempUrls = this.fileSystem.getTemporaryPublicFilePath(urls, expiration).iterator();
        
        entities.forEach(entity -> entity.setImageSrc(tempUrls.next()));
    }

}

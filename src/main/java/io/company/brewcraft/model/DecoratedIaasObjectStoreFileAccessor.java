package io.company.brewcraft.model;

import java.net.URI;

import io.company.brewcraft.dto.IaasObjectStoreFileDto;

public interface DecoratedIaasObjectStoreFileAccessor {
    URI getImageSrc();

    void setObjectStoreFile(IaasObjectStoreFileDto objectStoreFile);
}

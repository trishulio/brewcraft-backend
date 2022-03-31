package io.company.brewcraft.model;

import java.net.URI;
import java.net.URL;
import java.time.LocalDateTime;

import io.company.brewcraft.service.CrudEntity;

public class IaasObjectStoreFile extends BaseEntity implements UpdateIaasObjectStoreFile, CrudEntity<URI> {
    private URI fileKey;
    private LocalDateTime expiration;
    private URL fileUrl;

    public IaasObjectStoreFile() {
        super();
    }

    public IaasObjectStoreFile(URI id) {
        this();
        setId(id);
    }

    public IaasObjectStoreFile(URI fileKey, LocalDateTime expiration, URL fileUrl) {
        this(fileKey);
        setExpiration(expiration);
        setFileUrl(fileUrl);
    }

    @Override
    public URI getId() {
        return getFileKey();
    }

    @Override
    public void setId(URI id) {
        setFileKey(id);
    }

    @Override
    public URI getFileKey() {
        return this.fileKey;
    }

    @Override
    public void setFileKey(URI fileKey) {
        this.fileKey = fileKey;
    }

    @Override
    public LocalDateTime getExpiration() {
        return this.expiration;
    }

    @Override
    public void setExpiration(LocalDateTime expiration) {
        this.expiration = expiration;
    }

    @Override
    public URL getFileUrl() {
        return this.fileUrl;
    }

    @Override
    public void setFileUrl(URL fileUrl) {
        this.fileUrl = fileUrl;
    }

    @Override
    public Integer getVersion() {
        // Not implemented due to lack of use-case
        return null;
    }
}

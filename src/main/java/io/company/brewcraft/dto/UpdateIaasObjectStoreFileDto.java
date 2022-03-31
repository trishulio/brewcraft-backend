package io.company.brewcraft.dto;

import java.net.URI;
import java.time.LocalDateTime;

public class UpdateIaasObjectStoreFileDto extends BaseDto {
    private URI fileKey;
    private LocalDateTime expiration;

    public UpdateIaasObjectStoreFileDto() {
        super();
    }

    public UpdateIaasObjectStoreFileDto(URI fileKey) {
        setFileKey(fileKey);
    }

    public UpdateIaasObjectStoreFileDto(URI fileKey, LocalDateTime expiration) {
        this(fileKey);
        setExpiration(expiration);
    }

    public URI getFileKey() {
        return this.fileKey;
    }

    public void setFileKey(URI fileKey) {
        this.fileKey = fileKey;
    }

    public LocalDateTime getExpiration() {
        return this.expiration;
    }

    public void setExpiration(LocalDateTime expiration) {
        this.expiration = expiration;
    }
}

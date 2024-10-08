package io.company.brewcraft.dto;

import java.net.URI;
import java.time.LocalDateTime;

public class UpdateIaasObjectStoreFileDto extends BaseDto {
    private URI fileKey;
    private LocalDateTime minValidUntil;

    public UpdateIaasObjectStoreFileDto() {
        super();
    }

    public UpdateIaasObjectStoreFileDto(URI fileKey) {
        setFileKey(fileKey);
    }

    public UpdateIaasObjectStoreFileDto(URI fileKey, LocalDateTime minValidUntil) {
        this(fileKey);
        setMinValidUntil(minValidUntil);
    }

    public URI getFileKey() {
        return this.fileKey;
    }

    public void setFileKey(URI fileKey) {
        this.fileKey = fileKey;
    }

    public LocalDateTime getMinValidUntil() {
        return minValidUntil;
    }

    public void setMinValidUntil(LocalDateTime minValidUntil) {
        this.minValidUntil = minValidUntil;
    }
}

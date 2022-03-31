package io.company.brewcraft.dto;

import java.time.LocalDateTime;

public class AddIaasObjectStoreFileDto extends BaseDto {
    private LocalDateTime expiration;

    public AddIaasObjectStoreFileDto() {
        super();
    }

    public AddIaasObjectStoreFileDto(LocalDateTime expiration) {
        this.expiration = expiration;
    }

    public LocalDateTime getExpiration() {
        return this.expiration;
    }

    public void setExpiration(LocalDateTime expiration) {
        this.expiration = expiration;
    }
}

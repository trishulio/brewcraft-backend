package io.company.brewcraft.dto;

import java.net.URL;
import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;

public class AddTenantDto extends BaseDto {
    @Null
    private UUID id;

    @NotBlank
    private String name;

    @NotBlank
    private URL url;

    public AddTenantDto() {
        super();
    }
    
    public AddTenantDto(UUID id) {
        this();
        setId(id);
    }

    public AddTenantDto(UUID id, String name, URL url) {
        this(id);
        setName(name);
        setUrl(url);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }
}

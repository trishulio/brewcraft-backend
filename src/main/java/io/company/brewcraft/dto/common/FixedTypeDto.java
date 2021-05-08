package io.company.brewcraft.dto.common;


import com.fasterxml.jackson.annotation.JsonInclude;
import io.company.brewcraft.dto.BaseDto;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FixedTypeDto extends BaseDto {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

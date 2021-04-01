package io.company.brewcraft.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductMeasureDto extends BaseDto {

    @NotEmpty
    private String name;
    
    @Pattern(regexp = "^(?!\\s*$).+", message = "must not be blank")
    private String value;

    public ProductMeasureDto() {
        this(null, null);
    }

    public ProductMeasureDto(String name, String value) {
        setName(name);
        setValue(value);
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

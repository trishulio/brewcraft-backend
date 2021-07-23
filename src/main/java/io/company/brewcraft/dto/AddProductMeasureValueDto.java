package io.company.brewcraft.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddProductMeasureValueDto extends BaseDto {

    @NotNull
    private Long measureId;
    
    @Pattern(regexp = "^(?!\\s*$).+", message = "must not be blank")
    private String value;

    public AddProductMeasureValueDto() {
        this(null, null);
    }

    public AddProductMeasureValueDto(Long measureId, String value) {
        setMeasureId(measureId);
        setValue(value);
    }
    
    public Long getMeasureId() {
        return measureId;
    }

    public void setMeasureId(Long name) {
        this.measureId = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

package io.company.brewcraft.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

public class AddProductMeasureValueDto extends BaseDto {
    @NotNull
    private Long measureId;

    @NotNull
    private BigDecimal value;

    public AddProductMeasureValueDto() {
        this(null, null);
    }

    public AddProductMeasureValueDto(Long measureId, BigDecimal value) {
        setMeasureId(measureId);
        setValue(value);
    }

    public Long getMeasureId() {
        return measureId;
    }

    public void setMeasureId(Long name) {
        this.measureId = name;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}

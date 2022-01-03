package io.company.brewcraft.dto;

import java.math.BigDecimal;

public class ProductMeasureValueDto extends BaseDto {

    private Long id;

    private MeasureDto measure;

    private BigDecimal value;

    public ProductMeasureValueDto() {
        this(null, null, null);
    }

    public ProductMeasureValueDto(Long id, MeasureDto measure, BigDecimal value) {
        setId(id);
        setMeasure(measure);
        setValue(value);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MeasureDto getMeasure() {
        return measure;
    }

    public void setMeasure(MeasureDto measure) {
        this.measure = measure;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}

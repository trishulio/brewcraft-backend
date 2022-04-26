package io.company.brewcraft.dto;

import java.math.BigDecimal;

public class TaxRateDto extends BaseDto {
    private BigDecimal value;

    public TaxRateDto() {
        super();
    }

    public TaxRateDto(BigDecimal value) {
        this();
        setValue(value);
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}

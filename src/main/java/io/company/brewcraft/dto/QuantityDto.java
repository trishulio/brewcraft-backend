package io.company.brewcraft.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class QuantityDto extends BaseDto {

    private String symbol;
    private BigDecimal value;

    public QuantityDto() {
        this(null, null);
    }

    public QuantityDto(String symbol, BigDecimal value) {
        setSymbol(symbol);
        setValue(value);
    }

    public Number getValue() {
        BigDecimal value = null;
        if (this.value != null) {
            value = new BigDecimal(this.value.stripTrailingZeros().toPlainString());
        }
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        if (symbol != null) {            
            symbol = symbol.toLowerCase();
        }
        this.symbol = symbol;
    }
}

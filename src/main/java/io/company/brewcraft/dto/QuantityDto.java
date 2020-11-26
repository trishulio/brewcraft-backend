package io.company.brewcraft.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class QuantityDto extends BaseDto {

    private String symbol;
    private Number value;

    public QuantityDto() {
        this(null, null);
    }

    public QuantityDto(String symbol, Number value) {
        setSymbol(symbol);
        setValue(value);
    }

    public Number getValue() {
        return value;
    }

    public void setValue(Number value) {
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

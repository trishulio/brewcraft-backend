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
        //Working and converting between units with BigDecimals can add trailing zeros or result in a number displayed in scientific notation. 
        //This resolves those issues and returns exact number back to client without trailing zero's or in scientific notation.
        return new BigDecimal(value.stripTrailingZeros().toPlainString());
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

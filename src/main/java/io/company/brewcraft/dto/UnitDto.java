package io.company.brewcraft.dto;

public class UnitDto extends BaseDto {
    public static final String ATTR_SYMBOL = "symbol";

    private String symbol;

    public UnitDto() {
    }

    public UnitDto(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}

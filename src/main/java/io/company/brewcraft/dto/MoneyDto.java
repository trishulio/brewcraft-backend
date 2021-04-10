package io.company.brewcraft.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MoneyDto extends BaseDto {

    private String currency;
    private BigDecimal amount;

    public MoneyDto() {
        this(null, null);
    }

    public MoneyDto(String currency, BigDecimal amount) {
        setCurrency(currency);
        setAmount(amount);
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getAmount() {
        BigDecimal amount = null;
        if (this.amount != null) {
            amount = new BigDecimal(this.amount.stripTrailingZeros().toPlainString());
        }
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}

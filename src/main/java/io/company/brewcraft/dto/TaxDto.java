package io.company.brewcraft.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public class TaxDto {
    private MoneyDto amount;

    public TaxDto() {
    }

    public TaxDto(MoneyDto amount) {
        setAmount(amount);
    }

    public MoneyDto getAmount() {
        return amount;
    }

    public void setAmount(MoneyDto amount) {
        this.amount = amount;
    }
}

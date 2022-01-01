package io.company.brewcraft.dto;

public class TaxDto extends BaseDto {
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

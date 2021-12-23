package io.company.brewcraft.dto;


public class FreightDto extends BaseDto {
    private MoneyDto amount;

    public FreightDto() {
    }

    public FreightDto(MoneyDto amount) {
        setAmount(amount);
    }

    public MoneyDto getAmount() {
        return amount;
    }

    public void setAmount(MoneyDto amount) {
        this.amount = amount;
    }
}

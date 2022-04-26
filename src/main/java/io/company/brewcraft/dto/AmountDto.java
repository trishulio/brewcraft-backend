package io.company.brewcraft.dto;

public class AmountDto extends BaseDto {
    private MoneyDto total;
    private MoneyDto subTotal;
    private TaxAmountDto taxAmount;

    public AmountDto() {
        super();
    }

    public AmountDto(MoneyDto subTotal) {
        this();
        setSubTotal(subTotal);
    }

    public AmountDto(MoneyDto total, MoneyDto subTotal, TaxAmountDto taxAmount) {
        this(subTotal);
        setTotal(total);
        setTaxAmount(taxAmount);
    }

    public MoneyDto getTotal() {
        return total;
    }

    public void setTotal(MoneyDto total) {
        this.total = total;
    }

    public MoneyDto getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(MoneyDto subTotal) {
        this.subTotal = subTotal;
    }

    public TaxAmountDto getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(TaxAmountDto taxAmount) {
        this.taxAmount = taxAmount;
    }
}

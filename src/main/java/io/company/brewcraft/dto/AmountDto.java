package io.company.brewcraft.dto;

public class AmountDto extends BaseDto {
    private MoneyDto totalAmount;
    private MoneyDto subTotal;
    private TaxAmountDto taxAmount;

    public AmountDto() {
        super();
    }

    public AmountDto(MoneyDto subTotal) {
        this();
        setSubTotal(subTotal);
    }

    public AmountDto(MoneyDto totalAmount, MoneyDto subTotal, TaxAmountDto taxAmount) {
        this(subTotal);
        setTotalAmount(totalAmount);
        setTaxAmount(taxAmount);
    }

    public MoneyDto getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(MoneyDto totalAmount) {
        this.totalAmount = totalAmount;
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

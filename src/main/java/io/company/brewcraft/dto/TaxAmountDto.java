package io.company.brewcraft.dto;

public class TaxAmountDto extends BaseDto {
    private MoneyDto pstAmount;

    private MoneyDto gstAmount;

    private MoneyDto hstAmount;

    public TaxAmountDto() {
        super();
    }

    public TaxAmountDto(MoneyDto pstAmount, MoneyDto gstAmount, MoneyDto hstAmount) {
        this();
        setPstAmount(pstAmount);
        setGstAmount(gstAmount);
        setHstAmount(hstAmount);
    }

    public TaxAmountDto(MoneyDto pstAmount, MoneyDto gstAmount) {
        this(pstAmount, gstAmount, null);
    }

    public TaxAmountDto(MoneyDto hstAmount) {
        this(null, null, hstAmount);
    }

    public MoneyDto getPstAmount() {
        return pstAmount;
    }

    public void setPstAmount(MoneyDto pstAmount) {
        this.pstAmount = pstAmount;
    }

    public MoneyDto getGstAmount() {
        return gstAmount;
    }

    public void setGstAmount(MoneyDto gstAmount) {
        this.gstAmount = gstAmount;
    }

    public MoneyDto getHstAmount() {
        return hstAmount;
    }

    public void setHstAmount(MoneyDto hstAmount) {
        this.hstAmount = hstAmount;
    }
}

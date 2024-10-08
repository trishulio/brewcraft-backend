package io.company.brewcraft.dto;

public class TaxDto extends BaseDto {
    private TaxRateDto gstRate;
    private TaxRateDto pstRate;
    private TaxRateDto hstRate;

    public TaxDto() {
        super();
    }

    public TaxDto(TaxRateDto hstRate) {
        this();
        setHstRate(hstRate);
    }

    public TaxDto(TaxRateDto pstRate, TaxRateDto gstRate) {
        this();
        setPstRate(pstRate);
        setGstRate(gstRate);
    }

    public TaxRateDto getGstRate() {
        return gstRate;
    }

    public void setGstRate(TaxRateDto gstRate) {
        this.gstRate = gstRate;
    }

    public TaxRateDto getPstRate() {
        return pstRate;
    }

    public void setPstRate(TaxRateDto pstRate) {
        this.pstRate = pstRate;
    }

    public TaxRateDto getHstRate() {
        return hstRate;
    }

    public void setHstRate(TaxRateDto hstRate) {
        this.hstRate = hstRate;
    }
}

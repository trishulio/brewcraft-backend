package io.company.brewcraft.model;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

import io.company.brewcraft.util.validator.Validator;

@Embeddable
public class Tax extends BaseEntity {
    public static final String FIELD_GST_RATE = "gstRate";
    public static final String FIELD_HST_RATE = "hstRate";
    public static final String FIELD_PST_RATE = "pstRate";

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "value", column = @Column(name = "gst_rate"))
    })
    private TaxRate gstRate;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "value", column = @Column(name = "pst_rate"))
    })
    private TaxRate pstRate;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "value", column = @Column(name = "hst_rate"))
    })
    private TaxRate hstRate;

    public Tax() {
        super();
    }

    public Tax(TaxRate hstRate) {
        setHstRate(hstRate);
    }

    public Tax(TaxRate pstRate, TaxRate gstRate) {
        this();
        setPstRate(pstRate);
        setGstRate(gstRate);
    }

    public TaxRate getGstRate() {
        return gstRate;
    }

    public void setGstRate(TaxRate gstRate) {
        Validator.assertion(gstRate == null || (gstRate != null && getHstRate() == null), IllegalArgumentException.class, "Cannot set GST when HST is present. Remove HST");
        this.gstRate = gstRate;
    }

    public TaxRate getPstRate() {
        return pstRate;
    }

    public void setPstRate(TaxRate pstRate) {
        Validator.assertion(pstRate == null || (pstRate != null && getHstRate() == null), IllegalArgumentException.class, "Cannot set PST when HST is present. Remove HST");
        this.pstRate = pstRate;
    }

    public TaxRate getHstRate() {
        return hstRate;
    }

    public void setHstRate(TaxRate hstRate) {
        if (hstRate != null) {
            Validator.assertion(getPstRate() == null, IllegalArgumentException.class, "Cannot set HST when PST is present. Remove PST");
            Validator.assertion(getGstRate() == null, IllegalArgumentException.class, "Cannot set HST when GST is present. Remove GST");
        }
        this.hstRate = hstRate;
    }
}

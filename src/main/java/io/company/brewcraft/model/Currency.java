package io.company.brewcraft.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity(name = "currency")
@JsonIgnoreProperties({ "hibernateLazyInitializer" })
public class Currency extends BaseEntity implements BaseCurrency, UpdateCurrency {
    public static final String FIELD_NUMERIC_CODE = "numericCode";
    public static final String FIELD_CODE = "code";

    @Id
    @Column(name = "numeric_code")
    private Integer numericCode;

    @Column(name = "code", nullable = false, length = 3)
    private String code;

    public Currency() {
        this(null, null);
    }

    public Currency(Integer numericCode, String code) {
        setNumericCode(numericCode);
        setCode(code);
    }

    @Override
    public Integer getNumericCode() {
        return numericCode;
    }

    @Override
    public void setNumericCode(Integer numericCode) {
        this.numericCode = numericCode;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public void setCode(String code) {
        this.code = code;
    }
}

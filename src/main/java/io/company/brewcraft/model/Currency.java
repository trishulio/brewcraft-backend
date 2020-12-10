package io.company.brewcraft.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "currency")
public class Currency extends BaseEntity {

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

    public Integer getNumericCode() {
        return numericCode;
    }

    public void setNumericCode(Integer numericCode) {
        this.numericCode = numericCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}

package io.company.brewcraft.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "currency")
public class Currency extends BaseEntity {

    @Id
    private Integer id;

    @Column(name = "code")
    private String code;

    public Currency() {
        this(null, null);
    }
    
    public Currency(Integer id, String code) {
        setId(id);
        setCode(code);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}

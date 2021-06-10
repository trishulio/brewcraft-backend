package io.company.brewcraft.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity(name = "BREW_LOG_TYPE")
public class BrewLogType extends BaseEntity implements BaseBrewLogType, Identified<Long> {
    public static final String FIELD_ID = "id";
    public static final String FIELD_NAME = "name";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "brew_log_type_generator")
    @SequenceGenerator(name = "brew_log_type_generator", sequenceName = "brew_log_type_sequence", allocationSize = 1)
    private Long id;
    
    private String name;
    
    public BrewLogType() {
    } 
    
    public BrewLogType(Long id) {
        this();
        setId(id);
    }

    public BrewLogType(Long id, String name) {
    	this(id);
    	setName(name);
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

}

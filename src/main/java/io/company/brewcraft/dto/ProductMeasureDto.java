package io.company.brewcraft.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductMeasureDto extends BaseDto {
	
	private Long id;

    private String name;
    
    public ProductMeasureDto() {
    }
    
    public ProductMeasureDto(Long id) {
    	setId(id);
    }

    public ProductMeasureDto(Long id, String name) {
    	this(id);
    	setName(name);
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

package io.company.brewcraft.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductMeasureValueDto extends BaseDto {
	
	private Long id;

    private MeasureDto measure;
    
    private String value;

    public ProductMeasureValueDto() {
        this(null, null, null);
    }

    public ProductMeasureValueDto(Long id, MeasureDto measure, String value) {
    	setId(id);
        setMeasure(measure);
        setValue(value);
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public MeasureDto getMeasure() {
        return measure;
    }

    public void setMeasure(MeasureDto measure) {
        this.measure = measure;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

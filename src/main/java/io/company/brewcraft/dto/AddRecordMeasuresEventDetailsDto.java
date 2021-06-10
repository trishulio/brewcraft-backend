package io.company.brewcraft.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

public class AddRecordMeasuresEventDetailsDto {
        
    @NotNull
    private List<ProductMeasureDto> recordedMeasures;

    public AddRecordMeasuresEventDetailsDto(@NotNull List<ProductMeasureDto> recordedMeasures) {
        super();
        this.recordedMeasures = recordedMeasures;
    }

    public List<ProductMeasureDto> getRecordedMeasures() {
        return recordedMeasures;
    }

    public void setRecordedMeasures(List<ProductMeasureDto> recordedMeasures) {
        this.recordedMeasures = recordedMeasures;
    }    
}

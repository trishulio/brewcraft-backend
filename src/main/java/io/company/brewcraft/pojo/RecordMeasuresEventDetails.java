package io.company.brewcraft.pojo;

import java.util.List;

import io.company.brewcraft.model.BrewMeasureValue;

public class RecordMeasuresEventDetails implements IRecordMeasuresEventDetails {
    
    private List<BrewMeasureValue> recordedMeasures;

    public RecordMeasuresEventDetails(List<BrewMeasureValue> recordedMeasures) {
        super();
        this.recordedMeasures = recordedMeasures;
    }

    public List<BrewMeasureValue> getRecordedMeasures() {
        return recordedMeasures;
    }

    public void setRecordedMeasures(List<BrewMeasureValue> recordedMeasures) {
        this.recordedMeasures = recordedMeasures;
    }
    
}

package io.company.brewcraft.pojo;

import java.util.List;

import io.company.brewcraft.model.BrewMeasureValue;

public interface IRecordMeasuresEventDetails {
    
    List<BrewMeasureValue> getRecordedMeasures();

}

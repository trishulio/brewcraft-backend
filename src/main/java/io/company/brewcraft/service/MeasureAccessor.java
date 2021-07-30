package io.company.brewcraft.service;

import io.company.brewcraft.model.Measure;

public interface MeasureAccessor {
    final String ATTR_MEASURE = "measure";

    Measure getMeasure();
    
    void setMeasure(Measure measure);
}

package io.company.brewcraft.pojo;

import io.company.brewcraft.model.BrewLog;

public class RecordMeasuresEvent extends BaseBrewEvent implements IBrewEvent<RecordMeasuresEventDetails>, IRecordMeasuresEvent {
    
    public static final String EVENT_NAME = "RECORD_MEASURES";
    
    private RecordMeasuresEventDetails details;

    public RecordMeasuresEvent(BrewLog brewLog, RecordMeasuresEventDetails details) {
        super(brewLog);
        this.details = details;
    }

    public RecordMeasuresEventDetails getDetails() {
        return details;
    }

    public void setDetails(RecordMeasuresEventDetails details) {
        this.details = details;
    }
    
}

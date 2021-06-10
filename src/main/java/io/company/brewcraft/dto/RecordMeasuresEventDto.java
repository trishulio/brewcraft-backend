package io.company.brewcraft.dto;

import io.company.brewcraft.pojo.IBrewEventDto;
import io.company.brewcraft.pojo.RecordMeasuresEventDetails;

public class RecordMeasuresEventDto extends BaseDto implements IBrewEventDto<RecordMeasuresEventDetails> {
    
    private BrewLogDto log;

    private RecordMeasuresEventDetails details;

    public RecordMeasuresEventDto(BrewLogDto log, RecordMeasuresEventDetails details) {
        super();
        this.log = log;
        this.details = details;
    }

    public BrewLogDto getLog() {
        return log;
    }

    public void setLog(BrewLogDto log) {
        this.log = log;
    }

    public RecordMeasuresEventDetails getDetails() {
        return details;
    }

    public void setDetails(RecordMeasuresEventDetails details) {
        this.details = details;
    }

}

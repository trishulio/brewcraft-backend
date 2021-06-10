package io.company.brewcraft.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class AddRecordMeasuresEventDto extends BaseDto {
    
    @Valid
    @NotNull
    private AddBrewLogDto log;
    
    @Valid
    @NotNull
    private AddRecordMeasuresEventDetailsDto details;

    public AddRecordMeasuresEventDto(@NotNull AddBrewLogDto log, @NotNull AddRecordMeasuresEventDetailsDto details) {
        super();
        this.log = log;
        this.details = details;
    }

    public AddBrewLogDto getLog() {
        return log;
    }

    public void setLog(AddBrewLogDto log) {
        this.log = log;
    }

    public AddRecordMeasuresEventDetailsDto getDetails() {
        return details;
    }

    public void setDetails(AddRecordMeasuresEventDetailsDto details) {
        this.details = details;
    }

}

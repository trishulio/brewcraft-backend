package io.company.brewcraft.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class AddBrewTransferEventDto extends BaseDto {
    
    @Valid
    @NotNull
    private AddBrewLogDto log;
    
    @Valid
    @NotNull
    private AddBrewTransferEventDetailsDto details;

    public AddBrewTransferEventDto(@NotNull AddBrewLogDto log, @NotNull AddBrewTransferEventDetailsDto details) {
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

    public AddBrewTransferEventDetailsDto getDetails() {
        return details;
    }

    public void setDetails(AddBrewTransferEventDetailsDto details) {
        this.details = details;
    }

}

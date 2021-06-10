package io.company.brewcraft.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class AddAddMaterialsEventDto extends BaseDto {
    
    @Valid
    @NotNull
    private AddBrewLogDto log;
    
    @Valid
    @NotNull
    private AddAddMaterialsEventDetailsDto details;

    public AddAddMaterialsEventDto(@NotNull AddBrewLogDto log, @NotNull AddAddMaterialsEventDetailsDto details) {
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

    public AddAddMaterialsEventDetailsDto getDetails() {
        return details;
    }

    public void setDetails(AddAddMaterialsEventDetailsDto details) {
        this.details = details;
    }

}

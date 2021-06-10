package io.company.brewcraft.dto;

import io.company.brewcraft.pojo.AddMaterialsEventDetails;
import io.company.brewcraft.pojo.IBrewEventDto;

public class AddMaterialsEventDto extends BaseDto implements IBrewEventDto<AddMaterialsEventDetails> {
    
    private BrewLogDto log;

    private AddMaterialsEventDetails details;

    public AddMaterialsEventDto(BrewLogDto log, AddMaterialsEventDetails details) {
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

    public AddMaterialsEventDetails getDetails() {
        return details;
    }

    public void setDetails(AddMaterialsEventDetails details) {
        this.details = details;
    }

}

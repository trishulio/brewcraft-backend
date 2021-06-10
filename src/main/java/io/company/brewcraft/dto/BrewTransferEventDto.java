package io.company.brewcraft.dto;

import io.company.brewcraft.pojo.BrewTransferEventDetails;
import io.company.brewcraft.pojo.IBrewEventDto;

public class BrewTransferEventDto extends BaseDto implements IBrewEventDto<BrewTransferEventDetails> {
    
    private BrewLogDto log;

    private BrewTransferEventDetails details;

    public BrewTransferEventDto(BrewLogDto log, BrewTransferEventDetails details) {
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

    public BrewTransferEventDetails getDetails() {
        return details;
    }

    public void setDetails(BrewTransferEventDetails details) {
        this.details = details;
    }

}

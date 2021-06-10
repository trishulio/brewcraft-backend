package io.company.brewcraft.dto;

import io.company.brewcraft.pojo.IBrewEventDto;
import io.company.brewcraft.pojo.SplitBrewEventDetails;

public class SplitBrewEventDto extends BaseDto implements IBrewEventDto<SplitBrewEventDetails> {
    
    private BrewLogDto log;

    private SplitBrewEventDetails details;

    public SplitBrewEventDto(BrewLogDto log, SplitBrewEventDetails details) {
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

    public SplitBrewEventDetails getDetails() {
        return details;
    }

    public void setDetails(SplitBrewEventDetails details) {
        this.details = details;
    }

}

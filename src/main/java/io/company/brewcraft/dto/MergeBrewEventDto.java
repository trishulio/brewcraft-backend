package io.company.brewcraft.dto;

import io.company.brewcraft.pojo.IBrewEventDto;
import io.company.brewcraft.pojo.MergeBrewEventDetails;

public class MergeBrewEventDto extends BaseDto implements IBrewEventDto<MergeBrewEventDetails> {
    
    private BrewLogDto log;

    private MergeBrewEventDetails details;

    public MergeBrewEventDto(BrewLogDto log, MergeBrewEventDetails details) {
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

    public MergeBrewEventDetails getDetails() {
        return details;
    }

    public void setDetails(MergeBrewEventDetails details) {
        this.details = details;
    }

}

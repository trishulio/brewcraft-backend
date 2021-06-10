package io.company.brewcraft.pojo;

import io.company.brewcraft.model.BrewLog;

public class SplitBrewEvent extends BaseBrewEvent implements IBrewEvent<SplitBrewEventDetails>, ISplitBrewEvent {
    
    public static final String EVENT_NAME = "SPLIT";
    
    private SplitBrewEventDetails details;

    public SplitBrewEvent(BrewLog brewLog, SplitBrewEventDetails details) {
        super(brewLog);
        this.details = details;
    }

    public SplitBrewEventDetails getDetails() {
        return details;
    }

    public void setDetails(SplitBrewEventDetails details) {
        this.details = details;
    }


}

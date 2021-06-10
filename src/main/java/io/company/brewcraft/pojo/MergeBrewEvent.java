package io.company.brewcraft.pojo;

import io.company.brewcraft.model.BrewLog;

public class MergeBrewEvent extends BaseBrewEvent implements IBrewEvent<MergeBrewEventDetails>, IMergeBrewEvent {
    
    public static final String EVENT_NAME = "MERGE";
    
    private MergeBrewEventDetails details;

    public MergeBrewEvent(BrewLog brewLog, MergeBrewEventDetails details) {
        super(brewLog);
        this.details = details;
    }

    public MergeBrewEventDetails getDetails() {
        return details;
    }

    public void setDetails(MergeBrewEventDetails details) {
        this.details = details;
    }

}

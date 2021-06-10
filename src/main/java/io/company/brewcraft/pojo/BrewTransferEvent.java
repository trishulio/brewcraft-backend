package io.company.brewcraft.pojo;

import io.company.brewcraft.model.BrewLog;

public class BrewTransferEvent extends BaseBrewEvent implements IBrewEvent<BrewTransferEventDetails>, IBrewTransferEvent {
    
    public static final String EVENT_NAME = "TRANSFER";
    
    private BrewTransferEventDetails details;

    public BrewTransferEvent(BrewLog brewLog, BrewTransferEventDetails details) {
        super(brewLog);
        this.details = details;
    }

    public BrewTransferEventDetails getDetails() {
        return details;
    }

    public void setDetails(BrewTransferEventDetails details) {
        this.details = details;
    }
    
}

package io.company.brewcraft.pojo;

import io.company.brewcraft.model.BrewLog;

public class BaseBrewEvent {
            
    private BrewLog log;

    public BaseBrewEvent(BrewLog log) {
        super();
        this.log = log;
    }

    public BrewLog getLog() {
        return log;
    }

    public void setLog(BrewLog log) {
        this.log = log;
    }
    
}

package io.company.brewcraft.pojo;

import io.company.brewcraft.model.BrewLog;

public interface IBrewTransferEvent {
    
    BrewLog getLog();
    
    IBrewTransferEventDetails getDetails();

}

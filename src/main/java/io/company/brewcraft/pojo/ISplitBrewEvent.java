package io.company.brewcraft.pojo;

import io.company.brewcraft.model.BrewLog;

public interface ISplitBrewEvent {
    
    BrewLog getLog();
    
    ISplitBrewEventDetails getDetails();

}

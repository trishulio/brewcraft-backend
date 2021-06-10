package io.company.brewcraft.pojo;

import io.company.brewcraft.model.BrewLog;

public interface IMergeBrewEvent {
    
    BrewLog getLog();
    
    IMergeBrewEventDetails getDetails();

}

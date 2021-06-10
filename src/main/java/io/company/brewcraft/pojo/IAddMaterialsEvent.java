package io.company.brewcraft.pojo;

import io.company.brewcraft.model.BrewLog;

public interface IAddMaterialsEvent {
    
    BrewLog getLog();
    
    IAddMaterialsEventDetails getDetails();

}

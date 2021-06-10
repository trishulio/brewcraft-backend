package io.company.brewcraft.pojo;

import io.company.brewcraft.model.BrewLog;

public class AddMaterialsEvent extends BaseBrewEvent implements IBrewEvent<AddMaterialsEventDetails>, IAddMaterialsEvent {
    
    public static final String EVENT_NAME = "ADD_MATERIALS";
    
    private AddMaterialsEventDetails details;

    public AddMaterialsEvent(BrewLog brewLog, AddMaterialsEventDetails details) {
        super(brewLog);
        this.details = details;
    }

    public AddMaterialsEventDetails getDetails() {
        return details;
    }

    public void setDetails(AddMaterialsEventDetails details) {
        this.details = details;
    }
    
}

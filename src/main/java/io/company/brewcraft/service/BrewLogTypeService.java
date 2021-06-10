package io.company.brewcraft.service;

import java.util.List;
import java.util.Set;

import io.company.brewcraft.model.BrewLogType;

public interface BrewLogTypeService {
    
    public BrewLogType getType(String name);
    
    public List<BrewLogType> getTypes(Set<String> names);

}

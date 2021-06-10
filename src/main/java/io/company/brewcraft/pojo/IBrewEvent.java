package io.company.brewcraft.pojo;

import io.company.brewcraft.model.BrewLog;

public interface IBrewEvent<T> {
    
    BrewLog getLog();
    
    T getDetails();

}

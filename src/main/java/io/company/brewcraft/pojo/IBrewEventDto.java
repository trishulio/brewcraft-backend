package io.company.brewcraft.pojo;

import io.company.brewcraft.dto.BrewLogDto;

public interface IBrewEventDto<T> {
    
    BrewLogDto getLog();
    
    T getDetails();

}

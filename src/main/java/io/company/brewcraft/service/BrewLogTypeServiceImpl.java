package io.company.brewcraft.service;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.transaction.annotation.Transactional;

import io.company.brewcraft.model.BrewLogType;
import io.company.brewcraft.repository.BrewLogTypeRepository;

@Transactional
public class BrewLogTypeServiceImpl implements BrewLogTypeService {
    private BrewLogTypeRepository brewLogTypeRepository;

    public BrewLogTypeServiceImpl(BrewLogTypeRepository brewLogTypeRepository) {
        this.brewLogTypeRepository = brewLogTypeRepository;
    }

    public BrewLogType getType(String name) {
        if (name == null) {
            throw new NullPointerException("Non-null type name expected");
        }

        BrewLogType type = null;
        Iterator<BrewLogType> it = this.brewLogTypeRepository.findByNames(Set.of(name)).iterator();
        if (it.hasNext()) {
            type = it.next();
        }
        
        return type;
    }
    
    public List<BrewLogType> getTypes(Set<String> names) {
        List<BrewLogType> types = null;
        
        if (names == null) {
            throw new NullPointerException("Non-null name-set is expected");

        } else if (!names.isEmpty()) {            
            types = (List<BrewLogType>) this.brewLogTypeRepository.findByNames(names);
        }
        
        return types;
    }
}

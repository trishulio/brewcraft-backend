package io.company.brewcraft.service;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.transaction.annotation.Transactional;

import io.company.brewcraft.model.BrewStageStatus;
import io.company.brewcraft.repository.BrewStageStatusRepository;

@Transactional
public class BrewStageStatusServiceImpl implements BrewStageStatusService {
    private BrewStageStatusRepository brewStageStatusRepository;

    public BrewStageStatusServiceImpl(BrewStageStatusRepository brewStageStatusRepository) {
        this.brewStageStatusRepository = brewStageStatusRepository;
    }

    public BrewStageStatus getStatus(String name) {
        if (name == null) {
            throw new NullPointerException("Non-null status name expected");
        }

        BrewStageStatus status = null;
        Iterator<BrewStageStatus> it = this.brewStageStatusRepository.findByNames(Set.of(name)).iterator();
        if (it.hasNext()) {
            status = it.next();
        }
        
        return status;
    }
    
    public List<BrewStageStatus> getStatuses(Set<String> names) {
        List<BrewStageStatus> statuses = null;
        
        if (names == null) {
            throw new NullPointerException("Non-null name-set is expected");

        } else if (!names.isEmpty()) {            
            statuses = (List<BrewStageStatus>) this.brewStageStatusRepository.findByNames(names);
        }
        
        return statuses;
    }
}

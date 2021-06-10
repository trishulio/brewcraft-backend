package io.company.brewcraft.service;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.transaction.annotation.Transactional;

import io.company.brewcraft.model.BrewTask;
import io.company.brewcraft.repository.BrewTaskRepository;

@Transactional
public class BrewTaskServiceImpl implements BrewTaskService {
    private BrewTaskRepository brewTaskRepository;

    public BrewTaskServiceImpl(BrewTaskRepository brewTaskRepository) {
        this.brewTaskRepository = brewTaskRepository;
    }

    public BrewTask getTask(String name) {
        if (name == null) {
            throw new NullPointerException("Non-null status name expected");
        }

        BrewTask task = null;
        Iterator<BrewTask> it = this.brewTaskRepository.findByNames(Set.of(name)).iterator();
        if (it.hasNext()) {
            task = it.next();
        }
        
        return task;
    }
    
    public List<BrewTask> getTasks(Set<String> names) {
        List<BrewTask> statuses = null;
        
        if (names == null) {
            throw new NullPointerException("Non-null name-set is expected");

        } else if (!names.isEmpty()) {            
            statuses = (List<BrewTask>) this.brewTaskRepository.findByNames(names);
        }
        
        return statuses;
    }
}

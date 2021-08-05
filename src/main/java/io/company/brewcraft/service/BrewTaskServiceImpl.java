package io.company.brewcraft.service;

import static io.company.brewcraft.repository.RepositoryUtil.pageRequest;

import java.util.Iterator;
import java.util.Set;
import java.util.SortedSet;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import io.company.brewcraft.model.BrewTask;
import io.company.brewcraft.repository.BrewTaskRepository;
import io.company.brewcraft.repository.SpecificationBuilder;

@Transactional
public class BrewTaskServiceImpl implements BrewTaskService {
    private BrewTaskRepository brewTaskRepository;

    public BrewTaskServiceImpl(BrewTaskRepository brewTaskRepository) {
        this.brewTaskRepository = brewTaskRepository;
    }

    public Page<BrewTask> getTasks(Set<Long> ids, int page, int size, SortedSet<String> sort, boolean orderAscending) {
        Specification<BrewTask> spec = SpecificationBuilder
                .builder()
                .in(BrewTask.FIELD_ID, ids)
                .build();

        Page<BrewTask> brewTasks = brewTaskRepository.findAll(spec, pageRequest(sort, orderAscending, page, size));

        return brewTasks;
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

}

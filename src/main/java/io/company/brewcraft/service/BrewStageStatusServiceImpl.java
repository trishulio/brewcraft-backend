package io.company.brewcraft.service;

import static io.company.brewcraft.repository.RepositoryUtil.pageRequest;

import java.util.Iterator;
import java.util.Set;
import java.util.SortedSet;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import io.company.brewcraft.model.BrewStageStatus;
import io.company.brewcraft.repository.BrewStageStatusRepository;
import io.company.brewcraft.repository.SpecificationBuilder;

@Transactional
public class BrewStageStatusServiceImpl implements BrewStageStatusService {
    private BrewStageStatusRepository brewStageStatusRepository;

    public BrewStageStatusServiceImpl(BrewStageStatusRepository brewStageStatusRepository) {
        this.brewStageStatusRepository = brewStageStatusRepository;
    }

    public Page<BrewStageStatus> getStatuses(Set<Long> ids, int page, int size, SortedSet<String> sort, boolean orderAscending) {
        Specification<BrewStageStatus> spec = SpecificationBuilder
                .builder()
                .in(BrewStageStatus.FIELD_ID, ids)
                .build();

        Page<BrewStageStatus> brewStageStatusPage = brewStageStatusRepository.findAll(spec, pageRequest(sort, orderAscending, page, size));

        return brewStageStatusPage;
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
}

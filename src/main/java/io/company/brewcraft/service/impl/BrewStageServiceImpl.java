package io.company.brewcraft.service.impl;

import static io.company.brewcraft.repository.RepositoryUtil.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.SortedSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import io.company.brewcraft.model.BaseBrewStage;
import io.company.brewcraft.model.Brew;
import io.company.brewcraft.model.BrewStage;
import io.company.brewcraft.model.BrewStageStatus;
import io.company.brewcraft.model.BrewTask;
import io.company.brewcraft.model.UpdateBrewStage;
import io.company.brewcraft.repository.BrewStageRepository;
import io.company.brewcraft.repository.Refresher;
import io.company.brewcraft.repository.WhereClauseBuilder;
import io.company.brewcraft.service.BaseService;
import io.company.brewcraft.service.BrewStageAccessor;
import io.company.brewcraft.service.BrewStageService;
import io.company.brewcraft.service.exception.EntityNotFoundException;

@Transactional
public class BrewStageServiceImpl extends BaseService implements BrewStageService {
    private static final Logger log = LoggerFactory.getLogger(BrewStageServiceImpl.class);

    private BrewStageRepository brewStageRepository;

    private Refresher<BrewStage, BrewStageAccessor> brewStageRefresher;

    public BrewStageServiceImpl(BrewStageRepository brewStageRepository, Refresher<BrewStage, BrewStageAccessor> brewStageRefresher) {
        this.brewStageRepository = brewStageRepository;
        this.brewStageRefresher = brewStageRefresher;
    }

    @Override
    public Page<BrewStage> getBrewStages(Set<Long> ids, Set<Long> brewIds, Set<Long> statusIds, Set<Long> taskIds,
            LocalDateTime startedAtFrom, LocalDateTime startedAtTo, LocalDateTime endedAtFrom,
            LocalDateTime endedAtTo, int page, int size, SortedSet<String> sort, boolean orderAscending) {
            Specification<BrewStage> spec = WhereClauseBuilder
                .builder()
                .in(BrewStage.FIELD_ID, ids)
                .in(new String[] {BrewStage.FIELD_BREW, Brew.FIELD_ID}, brewIds)
                .in(new String[] {BrewStage.FIELD_STATUS, BrewStageStatus.FIELD_ID}, statusIds)
                .in(new String[] {BrewStage.FIELD_TASK, BrewTask.FIELD_ID}, taskIds)
                .between(Brew.FIELD_STARTED_AT, startedAtFrom, startedAtTo)
                .between(Brew.FIELD_ENDED_AT, endedAtFrom, endedAtTo)
                .build();

            Page<BrewStage> brewPage = brewStageRepository.findAll(spec, pageRequest(sort, orderAscending, page, size));

            return brewPage;
    }

    @Override
    public BrewStage getBrewStage(Long brewStageId) {
        BrewStage brewStage = brewStageRepository.findById(brewStageId).orElse(null);

        return brewStage;
    }

    @Override
    public List<BrewStage> addBrewStages(List<BrewStage> brewStages) {
        brewStageRefresher.refresh(brewStages);

        List<BrewStage> addedBrewStages = brewStageRepository.saveAll(brewStages);
        brewStageRepository.flush();

        return addedBrewStages;
    }

    @Override
    public BrewStage putBrewStage(Long brewStageId, BrewStage putBrewStage) {
        brewStageRefresher.refresh(List.of(putBrewStage));

        BrewStage existingBrewStage = getBrewStage(brewStageId);

        if (existingBrewStage == null) {
            existingBrewStage = putBrewStage;
            existingBrewStage.setId(brewStageId);
        } else {
            existingBrewStage.optimisticLockCheck(putBrewStage);
            existingBrewStage.override(putBrewStage, getPropertyNames(BaseBrewStage.class));
        }

        BrewStage brewStage = brewStageRepository.saveAndFlush(existingBrewStage);

        return brewStage;
    }

    @Override
    public BrewStage patchBrewStage(Long brewStageId, BrewStage brewStagePatch) {
        BrewStage existingBrewStage = Optional.ofNullable(getBrewStage(brewStageId)).orElseThrow(() -> new EntityNotFoundException("BrewStage", brewStageId.toString()));

        brewStageRefresher.refresh(List.of(brewStagePatch));

        existingBrewStage.optimisticLockCheck(brewStagePatch);

        existingBrewStage.outerJoin(brewStagePatch, getPropertyNames(UpdateBrewStage.class));

        BrewStage brewStage = brewStageRepository.saveAndFlush(existingBrewStage);

        return brewStage;
    }

    @Override
    public void deleteBrewStage(Long brewStageId) {
        brewStageRepository.deleteById(brewStageId);
    }

    @Override
    public boolean brewStageExists(Long brewStageId) {
        return brewStageRepository.existsById(brewStageId);
    }
}

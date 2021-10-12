package io.company.brewcraft.service;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;

import io.company.brewcraft.dto.UpdateFinishedGood;
import io.company.brewcraft.model.BaseFinishedGood;
import io.company.brewcraft.model.BaseFinishedGoodMaterialPortion;
import io.company.brewcraft.model.BaseFinishedGoodMixturePortion;
import io.company.brewcraft.model.FinishedGood;
import io.company.brewcraft.model.FinishedGoodMaterialPortion;
import io.company.brewcraft.model.FinishedGoodMixturePortion;
import io.company.brewcraft.model.Identified;
import io.company.brewcraft.model.UpdateFinishedGoodMaterialPortion;
import io.company.brewcraft.model.UpdateFinishedGoodMixturePortion;
import io.company.brewcraft.repository.WhereClauseBuilder;
import io.company.brewcraft.service.exception.EntityNotFoundException;

@Transactional
public class FinishedGoodService extends BaseService implements CrudService<Long, FinishedGood, BaseFinishedGood<? extends BaseFinishedGoodMixturePortion<?>, ? extends BaseFinishedGoodMaterialPortion<?>>, UpdateFinishedGood<? extends UpdateFinishedGoodMixturePortion<?>, ? extends UpdateFinishedGoodMaterialPortion<?>>, FinishedGoodAccessor> {
    private static final Logger log = LoggerFactory.getLogger(FinishedGoodService.class);

    private final UpdateService<Long, FinishedGood, BaseFinishedGood<? extends BaseFinishedGoodMixturePortion<?>, ? extends BaseFinishedGoodMaterialPortion<?>>, UpdateFinishedGood<? extends UpdateFinishedGoodMixturePortion<?>, ? extends UpdateFinishedGoodMaterialPortion<?>>> updateService;
    private final FinishedGoodMixturePortionService fgMixturePortionService;
    private final FinishedGoodMaterialPortionService fgMaterialPortionService;
    private final RepoService<Long, FinishedGood, FinishedGoodAccessor> repoService;

    public FinishedGoodService(UpdateService<Long, FinishedGood, BaseFinishedGood<? extends BaseFinishedGoodMixturePortion<?>, ? extends BaseFinishedGoodMaterialPortion<?>>, UpdateFinishedGood<? extends UpdateFinishedGoodMixturePortion<?>, ? extends UpdateFinishedGoodMaterialPortion<?>>> updateService, FinishedGoodMixturePortionService fgMixturePortionService, FinishedGoodMaterialPortionService fgMaterialPortionService, RepoService<Long, FinishedGood, FinishedGoodAccessor> repoService) {
        this.updateService = updateService;
        this.fgMixturePortionService = fgMixturePortionService;
        this.fgMaterialPortionService = fgMaterialPortionService;
        this.repoService = repoService;
    }

    public Page<FinishedGood> getFinishedGoods(
            Set<Long> ids,
            Set<Long> excludeIds,
            Set<Long> skuIds,
            SortedSet<String> sortBy,
            boolean ascending,
            int page,
            int size
         ) {
        final Specification<FinishedGood> spec = WhereClauseBuilder
                                            .builder()
                                            .in(FinishedGood.FIELD_ID, ids)
                                            .not().in(FinishedGood.FIELD_ID, excludeIds)
                                            .in(FinishedGood.FIELD_SKU, skuIds)
                                            .build();

        return this.repoService.getAll(spec, sortBy, ascending, page, size);
    }

    @Override
    public FinishedGood get(Long id) {
        return this.repoService.get(id);
    }

    @Override
    public List<FinishedGood> getByIds(Collection<? extends Identified<Long>> idProviders) {
        return this.repoService.getByIds(idProviders);
    }

    @Override
    public List<FinishedGood> getByAccessorIds(Collection<? extends FinishedGoodAccessor> accessors) {
        return this.repoService.getByAccessorIds(accessors, accessor -> accessor.getFinishedGood());
    }

    @Override
    public boolean exists(Set<Long> ids) {
        return this.repoService.exists(ids);
    }

    @Override
    public boolean exist(Long id) {
        return this.repoService.exists(id);
    }

    @Override
    public int delete(Set<Long> ids) {
        return this.repoService.delete(ids);
    }

    @Override
    public int delete(Long id) {
        return this.repoService.delete(id);
    }

    @Override
    public List<FinishedGood> add(final List<BaseFinishedGood<? extends BaseFinishedGoodMixturePortion<?>, ? extends BaseFinishedGoodMaterialPortion<?>>> additions) {
        if (additions == null) {
            return null;
        }

        final List<FinishedGood> entities = this.updateService.getAddEntities(additions);

        for (int i = 0; i < additions.size(); i++) {
            final List<FinishedGoodMixturePortion> mixturePortions = this.fgMixturePortionService.getAddEntities((List<BaseFinishedGoodMixturePortion<?>>) additions.get(i).getMixturePortions());
            entities.get(i).setMixturePortions(mixturePortions);
        }

        return this.repoService.saveAll(entities);
    }

    @Override
    public List<FinishedGood> put(List<UpdateFinishedGood<? extends UpdateFinishedGoodMixturePortion<?>, ? extends UpdateFinishedGoodMaterialPortion<?>>> updates) {
        if (updates == null) {
            return null;
        }

        final List<FinishedGood> existing = this.repoService.getByIds(updates);
        final List<FinishedGood> updated = this.updateService.getPutEntities(existing, updates);

        final int length = Math.max(existing.size(), updates.size());
        for (int i = 0; i < length; i++) {
            final List<FinishedGoodMixturePortion> existingMixturePortions = i < existing.size() ? existing.get(i).getMixturePortions() : null;
            final List<? extends UpdateFinishedGoodMixturePortion<?>> updateMixturePortions = i < updates.size() ? updates.get(i).getMixturePortions() : null;

            final List<FinishedGoodMixturePortion> updatedMixturePortions = this.fgMixturePortionService.getPutEntities(existingMixturePortions, (List<UpdateFinishedGoodMixturePortion<?>>) updateMixturePortions);

            updated.get(i).setMixturePortions(updatedMixturePortions);

            final List<FinishedGoodMaterialPortion> existingMaterialPortions = i < existing.size() ? existing.get(i).getMaterialPortions() : null;
            final List<? extends UpdateFinishedGoodMaterialPortion<?>> updateMaterialPortions = i < updates.size() ? updates.get(i).getMaterialPortions() : null;

            final List<FinishedGoodMaterialPortion> updatedMaterialPortions = this.fgMaterialPortionService.getPutEntities(existingMaterialPortions, (List<UpdateFinishedGoodMaterialPortion<?>>) updateMaterialPortions);

            updated.get(i).setMaterialPortions(updatedMaterialPortions);
        }

        return this.repoService.saveAll(updated);
    }

    @Override
    public List<FinishedGood> patch(List<UpdateFinishedGood<? extends UpdateFinishedGoodMixturePortion<?>, ? extends UpdateFinishedGoodMaterialPortion<?>>> patches) {
        if (patches == null) {
            return null;
        }

        final List<FinishedGood> existing = this.repoService.getByIds(patches);

        if (existing.size() != patches.size()) {
            final Set<Long> existingIds = existing.stream().map(finishedGood -> finishedGood.getId()).collect(Collectors.toSet());
            final Set<Long> nonExistingIds = patches.stream().map(patch -> patch.getId()).filter(patchId -> !existingIds.contains(patchId)).collect(Collectors.toSet());

            throw new EntityNotFoundException(String.format("Cannot find finished goods with Ids: %s", nonExistingIds));
        }

        final List<FinishedGood> updated = this.updateService.getPatchEntities(existing, patches);

        for (int i = 0; i < existing.size(); i++) {
            final List<FinishedGoodMixturePortion> existingMixturePortions = existing.get(i).getMixturePortions();
            final List<? extends UpdateFinishedGoodMixturePortion<?>> updateMixturePortions = patches.get(i).getMixturePortions();

            final List<FinishedGoodMixturePortion> updatedMixturePortions = this.fgMixturePortionService.getPatchEntities(existingMixturePortions, (List<UpdateFinishedGoodMixturePortion<?>>) updateMixturePortions);

            updated.get(i).setMixturePortions(updatedMixturePortions);

            final List<FinishedGoodMaterialPortion> existingMaterialPortions = existing.get(i).getMaterialPortions();
            final List<? extends UpdateFinishedGoodMaterialPortion<?>> updateMaterialPortions = patches.get(i).getMaterialPortions();

            final List<FinishedGoodMaterialPortion> updatedMaterialPortions = this.fgMaterialPortionService.getPatchEntities(existingMaterialPortions, (List<UpdateFinishedGoodMaterialPortion<?>>) updateMaterialPortions);

            updated.get(i).setMaterialPortions(updatedMaterialPortions);
        }

        return this.repoService.saveAll(updated);
    }
}

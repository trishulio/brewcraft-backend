package io.company.brewcraft.service;

import java.util.ArrayList;
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

import io.company.brewcraft.dto.UpdateFinishedGoodLot;
import io.company.brewcraft.model.BaseFinishedGoodLot;
import io.company.brewcraft.model.BaseFinishedGoodLotFinishedGoodLotPortion;
import io.company.brewcraft.model.BaseFinishedGoodLotMaterialPortion;
import io.company.brewcraft.model.BaseFinishedGoodLotMixturePortion;
import io.company.brewcraft.model.Brew;
import io.company.brewcraft.model.BrewStage;
import io.company.brewcraft.model.FinishedGoodLot;
import io.company.brewcraft.model.FinishedGoodLotFinishedGoodLotPortion;
import io.company.brewcraft.model.FinishedGoodLotMaterialPortion;
import io.company.brewcraft.model.FinishedGoodLotMixturePortion;
import io.company.brewcraft.model.Identified;
import io.company.brewcraft.model.Mixture;
import io.company.brewcraft.model.MixturePortion;
import io.company.brewcraft.model.Product;
import io.company.brewcraft.model.Sku;
import io.company.brewcraft.model.UpdateFinishedGoodLotFinishedGoodLotPortion;
import io.company.brewcraft.model.UpdateFinishedGoodLotMaterialPortion;
import io.company.brewcraft.model.UpdateFinishedGoodLotMixturePortion;
import io.company.brewcraft.repository.WhereClauseBuilder;
import io.company.brewcraft.service.exception.EntityNotFoundException;

@Transactional
public class FinishedGoodLotService extends BaseService implements CrudService<Long, FinishedGoodLot, BaseFinishedGoodLot<? extends BaseFinishedGoodLotMixturePortion<?>, ? extends BaseFinishedGoodLotMaterialPortion<?>>, UpdateFinishedGoodLot<? extends UpdateFinishedGoodLotMixturePortion<?>, ? extends UpdateFinishedGoodLotMaterialPortion<?>>, FinishedGoodLotAccessor> {
    private static final Logger log = LoggerFactory.getLogger(FinishedGoodLotService.class);

    private final UpdateService<Long, FinishedGoodLot, BaseFinishedGoodLot<? extends BaseFinishedGoodLotMixturePortion<?>, ? extends BaseFinishedGoodLotMaterialPortion<?>>, UpdateFinishedGoodLot<? extends UpdateFinishedGoodLotMixturePortion<?>, ? extends UpdateFinishedGoodLotMaterialPortion<?>>> updateService;
    private final FinishedGoodLotMixturePortionService fgLotMixturePortionService;
    private final FinishedGoodLotMaterialPortionService fgLotMaterialPortionService;
    private final FinishedGoodLotFinishedGoodLotPortionService fgLotFinishedGoodPortionService;
    private final RepoService<Long, FinishedGoodLot, FinishedGoodLotAccessor> repoService;

    public FinishedGoodLotService(UpdateService<Long, FinishedGoodLot, BaseFinishedGoodLot<? extends BaseFinishedGoodLotMixturePortion<?>, ? extends BaseFinishedGoodLotMaterialPortion<?>>, UpdateFinishedGoodLot<? extends UpdateFinishedGoodLotMixturePortion<?>, ? extends UpdateFinishedGoodLotMaterialPortion<?>>> updateService, FinishedGoodLotMixturePortionService fgLotMixturePortionService, FinishedGoodLotMaterialPortionService fgLotMaterialPortionService, FinishedGoodLotFinishedGoodLotPortionService fgLotFinishedGoodPortionService, RepoService<Long, FinishedGoodLot, FinishedGoodLotAccessor> repoService) {
        this.updateService = updateService;
        this.fgLotMixturePortionService = fgLotMixturePortionService;
        this.fgLotMaterialPortionService = fgLotMaterialPortionService;
        this.fgLotFinishedGoodPortionService = fgLotFinishedGoodPortionService;
        this.repoService = repoService;
    }

    public Page<FinishedGoodLot> getFinishedGoodLots(
            Set<Long> ids,
            Set<Long> excludeIds,
            Set<Long> skuIds,
            Set<Long> mixtureIds,
            Set<Long> brewStageIds,
            Set<Long> brewIds,
            Set<String> brewBatchIds,
            Set<Long> productIds,
            SortedSet<String> sort,
            boolean orderAscending,
            int page,
            int size
         ) {
        final Specification<FinishedGoodLot> spec = WhereClauseBuilder
                                            .builder()
                                            .in(FinishedGoodLot.FIELD_ID, ids)
                                            .not().in(FinishedGoodLot.FIELD_ID, excludeIds)
                                            .in(new String[] { FinishedGoodLot.FIELD_SKU, Sku.FIELD_ID }, skuIds)
                                            .in(new String[] { FinishedGoodLot.FIELD_MIXTURE_PORTIONS, MixturePortion.FIELD_MIXTURE, Mixture.FIELD_ID}, mixtureIds)
                                            .in(new String[] { FinishedGoodLot.FIELD_MIXTURE_PORTIONS, MixturePortion.FIELD_MIXTURE, Mixture.FIELD_BREW_STAGE, BrewStage.FIELD_ID }, brewStageIds)
                                            .in(new String[] { FinishedGoodLot.FIELD_MIXTURE_PORTIONS, MixturePortion.FIELD_MIXTURE, Mixture.FIELD_BREW_STAGE, BrewStage.FIELD_BREW, Brew.FIELD_ID }, brewIds)
                                            .in(new String[] { FinishedGoodLot.FIELD_MIXTURE_PORTIONS, MixturePortion.FIELD_MIXTURE, Mixture.FIELD_BREW_STAGE, BrewStage.FIELD_BREW, Brew.FIELD_BATCH_ID }, brewBatchIds)
                                            .in(new String[] { FinishedGoodLot.FIELD_MIXTURE_PORTIONS, MixturePortion.FIELD_MIXTURE, Mixture.FIELD_BREW_STAGE, BrewStage.FIELD_BREW, Brew.FIELD_PRODUCT, Product.FIELD_ID }, productIds)
                                            .build();

        return this.repoService.getAll(spec, sort, orderAscending, page, size);
    }

    @Override
    public FinishedGoodLot get(Long id) {
        return this.repoService.get(id);
    }

    @Override
    public List<FinishedGoodLot> getByIds(Collection<? extends Identified<Long>> idProviders) {
        return this.repoService.getByIds(idProviders);
    }

    @Override
    public List<FinishedGoodLot> getByAccessorIds(Collection<? extends FinishedGoodLotAccessor> accessors) {
        return this.repoService.getByAccessorIds(accessors, accessor -> accessor.getFinishedGoodLot());
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
    public List<FinishedGoodLot> add(final List<BaseFinishedGoodLot<? extends BaseFinishedGoodLotMixturePortion<?>, ? extends BaseFinishedGoodLotMaterialPortion<?>>> additions) {
        if (additions == null) {
            return null;
        }

        final List<FinishedGoodLot> entities = this.updateService.getAddEntities(additions);

        for (int i = 0; i < additions.size(); i++) {
            final List<FinishedGoodLotMixturePortion> mixturePortions = this.fgLotMixturePortionService.getAddEntities((List<BaseFinishedGoodLotMixturePortion<?>>) additions.get(i).getMixturePortions());
            entities.get(i).setMixturePortions(mixturePortions);

            final List<FinishedGoodLotMaterialPortion> materialPortions = this.fgLotMaterialPortionService.getAddEntities((List<BaseFinishedGoodLotMaterialPortion<?>>) additions.get(i).getMaterialPortions());
            entities.get(i).setMaterialPortions(materialPortions);

            final List<FinishedGoodLotFinishedGoodLotPortion> finishedGoodLotPortions = this.fgLotFinishedGoodPortionService.getAddEntities((List<BaseFinishedGoodLotFinishedGoodLotPortion>) (List<? extends BaseFinishedGoodLotFinishedGoodLotPortion>) additions.get(i).getFinishedGoodLotPortions());
            entities.get(i).setFinishedGoodLotPortions(finishedGoodLotPortions);
        }

        return this.repoService.saveAll(entities);
    }

    @Override
    public List<FinishedGoodLot> put(List<UpdateFinishedGoodLot<? extends UpdateFinishedGoodLotMixturePortion<?>, ? extends UpdateFinishedGoodLotMaterialPortion<?>>> updates) {
        if (updates == null) {
            return null;
        }

        final List<FinishedGoodLot> existing = this.repoService.getByIds(updates);
        final List<FinishedGoodLot> updated = this.updateService.getPutEntities(existing, updates);

        final int length = Math.max(existing.size(), updates.size());
        for (int i = 0; i < length; i++) {
            final List<FinishedGoodLotMixturePortion> existingMixturePortions = i < existing.size() ? existing.get(i).getMixturePortions() : null;
            final List<? extends UpdateFinishedGoodLotMixturePortion<?>> updateMixturePortions = i < updates.size() ? updates.get(i).getMixturePortions() : null;

            final List<FinishedGoodLotMixturePortion> updatedMixturePortions = this.fgLotMixturePortionService.getPutEntities(existingMixturePortions, (List<UpdateFinishedGoodLotMixturePortion<?>>) updateMixturePortions);

            updated.get(i).setMixturePortions(updatedMixturePortions);

            final List<FinishedGoodLotMaterialPortion> existingMaterialPortions = i < existing.size() ? existing.get(i).getMaterialPortions() : null;
            final List<? extends UpdateFinishedGoodLotMaterialPortion<?>> updateMaterialPortions = i < updates.size() ? updates.get(i).getMaterialPortions() : null;

            final List<FinishedGoodLotMaterialPortion> updatedMaterialPortions = this.fgLotMaterialPortionService.getPutEntities(existingMaterialPortions, (List<UpdateFinishedGoodLotMaterialPortion<?>>) updateMaterialPortions);

            updated.get(i).setMaterialPortions(updatedMaterialPortions);

            final List<FinishedGoodLotFinishedGoodLotPortion> existingFinishedGoodLotPortions = i < existing.size() ? existing.get(i).getFinishedGoodLotPortions() : null;
            final List<? extends UpdateFinishedGoodLotFinishedGoodLotPortion> updateFinishedGoodLotPortions = i < updates.size() ? updates.get(i).getFinishedGoodLotPortions() : null;

            final List<FinishedGoodLotFinishedGoodLotPortion> updatedFinishedGoodLotPortions = this.fgLotFinishedGoodPortionService.getPutEntities(existingFinishedGoodLotPortions, (List<UpdateFinishedGoodLotFinishedGoodLotPortion>) updateFinishedGoodLotPortions);

            updated.get(i).setFinishedGoodLotPortions(updatedFinishedGoodLotPortions);
        }

        return this.repoService.saveAll(updated);
    }

    @Override
    public List<FinishedGoodLot> patch(List<UpdateFinishedGoodLot<? extends UpdateFinishedGoodLotMixturePortion<?>, ? extends UpdateFinishedGoodLotMaterialPortion<?>>> patches) {
        if (patches == null) {
            return null;
        }

        final List<FinishedGoodLot> existing = this.repoService.getByIds(patches);

        if (existing.size() != patches.size()) {
            final Set<Long> existingIds = existing.stream().map(finishedGood -> finishedGood.getId()).collect(Collectors.toSet());
            final Set<Long> nonExistingIds = patches.stream().map(patch -> patch.getId()).filter(patchId -> !existingIds.contains(patchId)).collect(Collectors.toSet());

            throw new EntityNotFoundException(String.format("Cannot find finished goods with Ids: %s", nonExistingIds));
        }

        final List<FinishedGoodLot> updated = this.updateService.getPatchEntities(existing, patches);

        for (int i = 0; i < existing.size(); i++) {
            final List<FinishedGoodLotMixturePortion> existingMixturePortions = existing.get(i).getMixturePortions();
            final List<? extends UpdateFinishedGoodLotMixturePortion<?>> updateMixturePortions = patches.get(i).getMixturePortions();

            final List<FinishedGoodLotMixturePortion> updatedMixturePortions = this.fgLotMixturePortionService.getPatchEntities(existingMixturePortions, (List<UpdateFinishedGoodLotMixturePortion<?>>) updateMixturePortions);

            updated.get(i).setMixturePortions(updatedMixturePortions);

            final List<FinishedGoodLotMaterialPortion> existingMaterialPortions = existing.get(i).getMaterialPortions();
            final List<? extends UpdateFinishedGoodLotMaterialPortion<?>> updateMaterialPortions = patches.get(i).getMaterialPortions();

            final List<FinishedGoodLotMaterialPortion> updatedMaterialPortions = this.fgLotMaterialPortionService.getPatchEntities(existingMaterialPortions, (List<UpdateFinishedGoodLotMaterialPortion<?>>) updateMaterialPortions);

            updated.get(i).setMaterialPortions(updatedMaterialPortions);

            final List<FinishedGoodLotFinishedGoodLotPortion> existingFinishedGoodLotPortions = existing.get(i).getFinishedGoodLotPortions();
            final List<? extends UpdateFinishedGoodLotFinishedGoodLotPortion> updateFinishedGoodLotPortions = patches.get(i).getFinishedGoodLotPortions();

            final List<FinishedGoodLotFinishedGoodLotPortion> updatedFinishedGoodLotPortions = this.fgLotFinishedGoodPortionService.getPatchEntities(existingFinishedGoodLotPortions, (List<UpdateFinishedGoodLotFinishedGoodLotPortion>) updateFinishedGoodLotPortions);

            updated.get(i).setFinishedGoodLotPortions(updatedFinishedGoodLotPortions);
        }

        return this.repoService.saveAll(updated);
    }
}

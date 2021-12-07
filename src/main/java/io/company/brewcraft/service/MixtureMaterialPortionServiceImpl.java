package io.company.brewcraft.service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.stream.Collectors;

import javax.measure.Quantity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import io.company.brewcraft.model.BaseMixtureMaterialPortion;
import io.company.brewcraft.model.Brew;
import io.company.brewcraft.model.BrewStage;
import io.company.brewcraft.model.MaterialLot;
import io.company.brewcraft.model.MaterialPortion;
import io.company.brewcraft.model.Mixture;
import io.company.brewcraft.model.MixtureMaterialPortion;
import io.company.brewcraft.model.MixtureRecording;
import io.company.brewcraft.model.UpdateMixtureMaterialPortion;
import io.company.brewcraft.repository.WhereClauseBuilder;
import io.company.brewcraft.service.exception.EntityNotFoundException;
import io.company.brewcraft.service.exception.MaterialLotQuantityNotAvailableException;
import io.company.brewcraft.util.QuantityCalculator;
import io.company.brewcraft.util.validator.Validator;

@Transactional
public class MixtureMaterialPortionServiceImpl extends BaseMaterialPortionService implements MixtureMaterialPortionService {
    private static final Logger log = LoggerFactory.getLogger(MixtureMaterialPortionServiceImpl.class);

    private final RepoService<Long, MixtureMaterialPortion, MixtureMaterialPortionAccessor> repoService;

    private final UpdateService<Long, MixtureMaterialPortion, BaseMixtureMaterialPortion, UpdateMixtureMaterialPortion> updateService;

    public MixtureMaterialPortionServiceImpl(RepoService<Long, MixtureMaterialPortion, MixtureMaterialPortionAccessor> repoService, UpdateService<Long, MixtureMaterialPortion, BaseMixtureMaterialPortion, UpdateMixtureMaterialPortion> updateService, StockLotService stockLotService) {
        super(stockLotService);
        this.repoService = repoService;
        this.updateService = updateService;
    }

    @Override
    public Page<MixtureMaterialPortion> getMaterialPortions(Set<Long> ids, Set<Long> mixtureIds, Set<Long> materialLotIds, Set<Long> brewStageIds, Set<Long> brewIds, int page, int size, SortedSet<String> sort, boolean orderAscending) {
        Specification<MixtureMaterialPortion> spec = WhereClauseBuilder.builder()
                                                                  .in(MaterialPortion.FIELD_ID, ids)
                                                                  .in(new String[] {MixtureMaterialPortion.FIELD_MIXTURE, Mixture.FIELD_ID}, mixtureIds)
                                                                  .in(new String[] {MaterialPortion.MATERIAL_LOT, MaterialLot.FIELD_ID}, materialLotIds)
                                                                  .in(new String[] {MixtureRecording.FIELD_MIXTURE, Mixture.FIELD_BREW_STAGE, BrewStage.FIELD_ID}, brewStageIds)
                                                                  .in(new String[] {MixtureRecording.FIELD_MIXTURE, Mixture.FIELD_BREW_STAGE, BrewStage.FIELD_BREW, Brew.FIELD_ID}, brewIds)
                                                                  .build();

            Page<MixtureMaterialPortion> materialPortionsPage = repoService.getAll(spec, sort, orderAscending, page, size);

            return materialPortionsPage;
    }

    @Override
    public MixtureMaterialPortion getMaterialPortion(Long materialPortionId) {
        return this.repoService.get(materialPortionId);
    }

    @Override
    public List<MixtureMaterialPortion> addMaterialPortions(List<BaseMixtureMaterialPortion> materialPortions) {
        if (materialPortions == null) {
            return null;
        }

        List<Long> lotIds = materialPortions.stream().map(x -> x.getMaterialLot().getId()).collect(Collectors.toList());
        Validator.assertion(lotIds.size() == new HashSet<Long>(lotIds).size(), RuntimeException.class, "Material lot ids must be unique");

        materialPortions.forEach(materialPortion -> {
            Validator.assertion(new BigDecimal(materialPortion.getQuantity().getValue().toString()).compareTo(BigDecimal.ZERO) > 0, RuntimeException.class, "Quantities must be greater than 0");
        });

        Map<Long, Quantity<?>> lotToQuantity = materialPortions.stream().collect(Collectors.toMap(materialPortion -> materialPortion.getMaterialLot().getId(), BaseMixtureMaterialPortion::getQuantity));
        Map<Long, Boolean> quantityCheckresult = this.areQuantitiesAvailable(lotToQuantity);

        Set<Long> lotIdsWithUnavailableQuantity = quantityCheckresult.entrySet().stream().filter(entry -> entry.getValue() == false).map(Map.Entry::getKey).collect(Collectors.toSet());

        if (!lotIdsWithUnavailableQuantity.isEmpty()) {
            throw new MaterialLotQuantityNotAvailableException(lotIdsWithUnavailableQuantity);
        }

        final List<MixtureMaterialPortion> entities = this.updateService.getAddEntities(materialPortions);

        return this.repoService.saveAll(entities);
    }

    @Override
    public MixtureMaterialPortion addMaterialPortion(BaseMixtureMaterialPortion materialPortion) {
        return this.addMaterialPortions(List.of(materialPortion)).get(0);
    }

    @Override
    public List<MixtureMaterialPortion> putMaterialPortions(List<UpdateMixtureMaterialPortion> putMaterialPortions) {
        if (putMaterialPortions == null) {
            return null;
        }

        List<Long> lotIds = putMaterialPortions.stream().map(x -> x.getMaterialLot().getId()).collect(Collectors.toList());
        Validator.assertion(lotIds.size() == new HashSet<Long>(lotIds).size(), RuntimeException.class, "Material lot ids must be unique");

        final List<MixtureMaterialPortion> existingEntities = this.repoService.getByIds(putMaterialPortions);
        Map<Long, MixtureMaterialPortion> idToExistingEntity = existingEntities.stream().collect(Collectors.toMap(materialPortion -> materialPortion.getId(), materialPortion -> materialPortion));

        putMaterialPortions.forEach(putMaterialPortion -> {
            Validator.assertion(new BigDecimal(putMaterialPortion.getQuantity().getValue().toString()).compareTo(BigDecimal.ZERO) > 0, RuntimeException.class, "Quantity must be greater than 0");

            boolean isNewMaterialPortion = putMaterialPortion.getId() == null || !idToExistingEntity.containsKey(putMaterialPortion.getId());

            if (isNewMaterialPortion) {
                Boolean quantityAvailable = this.isQuantityAvailable(putMaterialPortion.getMaterialLot().getId(), putMaterialPortion.getQuantity());

                if (!quantityAvailable) {
                    throw new MaterialLotQuantityNotAvailableException(Set.of(putMaterialPortion.getMaterialLot().getId()));
                }
            } else {
                MixtureMaterialPortion existing = idToExistingEntity.get(putMaterialPortion.getId());

                boolean isPutOnSameLot = putMaterialPortion.getMaterialLot().getId() == existing.getMaterialLot().getId();

                //If put is on the same lot, we only need to check if the difference in quantity is available
                Quantity<?> quantityToCheck = isPutOnSameLot ? QuantityCalculator.subtract(putMaterialPortion.getQuantity(), existing.getQuantity()) : putMaterialPortion.getQuantity();

                if (new BigDecimal(quantityToCheck.getValue().toString()).compareTo(BigDecimal.ZERO) > 0) {
                    Boolean quantityAvailable = this.isQuantityAvailable(putMaterialPortion.getMaterialLot().getId(), quantityToCheck);

                    if (!quantityAvailable) {
                        throw new MaterialLotQuantityNotAvailableException(Set.of(putMaterialPortion.getMaterialLot().getId()));
                    }
                }
            }
        });

        final List<MixtureMaterialPortion> updated = this.updateService.getPutEntities(existingEntities, putMaterialPortions);

        return this.repoService.saveAll(updated);
    }

    @Override
    public List<MixtureMaterialPortion> patchMaterialPortions(List<UpdateMixtureMaterialPortion> patchMaterialPortions) {
        if (patchMaterialPortions == null) {
            return null;
        }

        List<Long> lotIds = patchMaterialPortions.stream().map(x -> x.getMaterialLot().getId()).collect(Collectors.toList());
        Validator.assertion(lotIds.size() == new HashSet<Long>(lotIds).size(), RuntimeException.class, "Material lot ids must be unique");

        final List<MixtureMaterialPortion> existingEntities = this.repoService.getByIds(patchMaterialPortions);
        Map<Long, MixtureMaterialPortion> idToExistingEntity = existingEntities.stream().collect(Collectors.toMap(materialPortion -> materialPortion.getId(), materialPortion -> materialPortion));

        if (existingEntities.size() != patchMaterialPortions.size()) {
            final Set<Long> existingIds = existingEntities.stream().map(materialPortion -> materialPortion.getId()).collect(Collectors.toSet());
            final Set<Long> nonExistingIds = patchMaterialPortions.stream().map(patch -> patch.getId()).filter(patchId -> !existingIds.contains(patchId)).collect(Collectors.toSet());

            throw new EntityNotFoundException(String.format("Cannot find material portion with Ids: %s", nonExistingIds));
        }

        patchMaterialPortions.forEach(patchMaterialPortion -> {
            if (patchMaterialPortion.getQuantity() != null) {
                Validator.assertion(new BigDecimal(patchMaterialPortion.getQuantity().getValue().toString()).compareTo(BigDecimal.ZERO) > 0, RuntimeException.class, "Quantity must be greater than 0");
            }

            MixtureMaterialPortion existing = idToExistingEntity.get(patchMaterialPortion.getId());

            boolean isPatchOnSameLot = patchMaterialPortion.getMaterialLot() == null || patchMaterialPortion.getMaterialLot().getId() == null
                    || patchMaterialPortion.getMaterialLot().getId() == existing.getMaterialLot().getId();

            boolean isQuantityCheckRequired = (isPatchOnSameLot && patchMaterialPortion.getQuantity() != null) || !isPatchOnSameLot;

            if (isQuantityCheckRequired) {
                Long lotIdToCheck = isPatchOnSameLot ? existing.getMaterialLot().getId() : patchMaterialPortion.getMaterialLot().getId();

                //If patch is on the same lot, we only need to check if the difference in quantity is available
                Quantity<?> quantityToCheck = isPatchOnSameLot ? QuantityCalculator.subtract(patchMaterialPortion.getQuantity(), existing.getQuantity()) :
                    patchMaterialPortion.getQuantity() != null ? patchMaterialPortion.getQuantity() : existing.getQuantity();

                if (new BigDecimal(quantityToCheck.getValue().toString()).compareTo(BigDecimal.ZERO) > 0) {
                    Boolean quantityAvailable = this.isQuantityAvailable(lotIdToCheck, quantityToCheck);

                    if (!quantityAvailable) {
                        throw new MaterialLotQuantityNotAvailableException(Set.of(lotIdToCheck));
                    }
                }
            }
        });

        final List<MixtureMaterialPortion> updated = this.updateService.getPatchEntities(existingEntities, patchMaterialPortions);

        return this.repoService.saveAll(updated);
    }

    @Override
    public int deleteMaterialPortions(Set<Long> materialPortionIds) {
        return this.repoService.delete(materialPortionIds);
    }

    @Override
    public boolean materialPortionExists(Long materialPortionId) {
        return this.repoService.exists(materialPortionId);
    }
}

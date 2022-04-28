package io.company.brewcraft.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
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

        Map<Long, Quantity<?>> lotIdToQuantity =  new HashMap<>();
        materialPortions.forEach(materialPortion -> {
            Quantity<?> quantity = materialPortion.getQuantity();
            Validator.assertion(((BigDecimal) quantity.getValue()).toBigInteger().compareTo(BigInteger.ZERO) > 0, RuntimeException.class, "Quantities must be greater than 0");

            Long materialLotId = materialPortion.getMaterialLot().getId();
            Quantity<?> total = lotIdToQuantity.get(materialLotId);
            lotIdToQuantity.put(materialLotId, total == null ? quantity : QuantityCalculator.INSTANCE.add(total, quantity));
        });

        Map<Long, Boolean> quantityCheckresult = this.areQuantitiesAvailable(lotIdToQuantity);

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

        Map<Long, Quantity<?>> lotIdToQuantity =  new HashMap<>();
        putMaterialPortions.forEach(materialPortion -> {
            Quantity<?> quantity = materialPortion.getQuantity();
            Validator.assertion(((BigDecimal)quantity.getValue()).toBigInteger().compareTo(BigInteger.ZERO) > 0, RuntimeException.class, "Quantities must be greater than 0");

            Long materialLotId = materialPortion.getMaterialLot().getId();
            Quantity<?> total = lotIdToQuantity.get(materialLotId);
            lotIdToQuantity.put(materialLotId, total == null ? quantity : QuantityCalculator.INSTANCE.add(total, quantity));
        });

        final List<MixtureMaterialPortion> existingEntities = this.repoService.getByIds(putMaterialPortions);

        Map<Long, Quantity<?>> existingLotIdToQuantity = new HashMap<>();
        existingEntities.forEach(existingEntity -> {
            Quantity<?> existingQuantity = existingEntity.getQuantity();
            Long lotId = existingEntity.getMaterialLot().getId();
            Quantity<?> total = existingLotIdToQuantity.get(lotId);

            existingLotIdToQuantity.put(lotId, total == null ? existingQuantity : QuantityCalculator.INSTANCE.add(total, existingQuantity));
        });

        Map<Long, Quantity<?>> lotQuantitiesToCheck = new HashMap<>();
        lotIdToQuantity.forEach((lotId, quantity) -> {
            Quantity<?> existingQuantity = existingLotIdToQuantity.get(lotId);

            if (existingQuantity != null) {
                Quantity<?> difference = QuantityCalculator.INSTANCE.subtract(quantity, existingQuantity);

                if (((BigDecimal)difference.getValue()).toBigInteger().compareTo(BigInteger.ZERO) > 0) {
                    // Quantity has been increased for an existing material portion, need to check if the additional quantity amount is available.
                    lotQuantitiesToCheck.put(lotId, difference);
                }
            } else {
                // Portion(s) from a new lot have been added, check if the total quantity amount is available.
                lotQuantitiesToCheck.put(lotId, quantity);
            }
        });

        if (lotQuantitiesToCheck.size() > 0) {
            Map<Long, Boolean> quantityCheckresult = this.areQuantitiesAvailable(lotQuantitiesToCheck);

            Set<Long> lotIdsWithUnavailableQuantity = quantityCheckresult.entrySet().stream().filter(entry -> entry.getValue() == false).map(Map.Entry::getKey).collect(Collectors.toSet());
            if (!lotIdsWithUnavailableQuantity.isEmpty()) {
                throw new MaterialLotQuantityNotAvailableException(lotIdsWithUnavailableQuantity);
            }
        }

        final List<MixtureMaterialPortion> updated = this.updateService.getPutEntities(existingEntities, putMaterialPortions);

        return this.repoService.saveAll(updated);
    }

    @Override
    public List<MixtureMaterialPortion> patchMaterialPortions(List<UpdateMixtureMaterialPortion> patchMaterialPortions) {
        if (patchMaterialPortions == null) {
            return null;
        }

        final List<MixtureMaterialPortion> existingEntities = this.repoService.getByIds(patchMaterialPortions);
        Map<Long, MixtureMaterialPortion> idToExistingEntity = existingEntities.stream().collect(Collectors.toMap(materialPortion -> materialPortion.getId(), materialPortion -> materialPortion));

        if (existingEntities.size() != patchMaterialPortions.size()) {
            final Set<Long> existingIds = existingEntities.stream().map(materialPortion -> materialPortion.getId()).collect(Collectors.toSet());
            final Set<Long> nonExistingIds = patchMaterialPortions.stream().map(patch -> patch.getId()).filter(patchId -> !existingIds.contains(patchId)).collect(Collectors.toSet());

            throw new EntityNotFoundException(String.format("Cannot find material portion with Ids: %s", nonExistingIds));
        }

        Map<Long, Quantity<?>> lotIdToQuantity =  new HashMap<>();
        patchMaterialPortions.forEach(materialPortion -> {
            MixtureMaterialPortion existingPortion = idToExistingEntity.get(materialPortion.getId());

            Quantity<?> quantity = materialPortion.getQuantity() != null ? materialPortion.getQuantity() : existingPortion.getQuantity();
            Validator.assertion(((BigDecimal)quantity.getValue()).toBigInteger().compareTo(BigInteger.ZERO) > 0, RuntimeException.class, "Quantities must be greater than 0");

            Long materialLotId = materialPortion.getMaterialLot() != null && materialPortion.getMaterialLot().getId() != null ? materialPortion.getMaterialLot().getId() : existingPortion.getMaterialLot().getId();
            Quantity<?> total = lotIdToQuantity.get(materialLotId);
            lotIdToQuantity.put(materialLotId, total == null ? quantity : QuantityCalculator.INSTANCE.add(total, quantity));
        });

        Map<Long, Quantity<?>> existingLotIdToQuantity = new HashMap<>();
        existingEntities.forEach(existingEntity -> {
            Quantity<?> existingQuantity = existingEntity.getQuantity();
            Long lotId = existingEntity.getMaterialLot().getId();
            Quantity<?> total = existingLotIdToQuantity.get(lotId);

            existingLotIdToQuantity.put(lotId, total == null ? existingQuantity : QuantityCalculator.INSTANCE.add(total, existingQuantity));
        });

        Map<Long, Quantity<?>> lotQuantitiesToCheck = new HashMap<>();
        lotIdToQuantity.forEach((lotId, quantity) -> {
            Quantity<?> existingQuantity = existingLotIdToQuantity.get(lotId);

            if (existingQuantity != null) {
                Quantity<?> difference = QuantityCalculator.INSTANCE.subtract(quantity, existingQuantity);

                if (((BigDecimal)difference.getValue()).toBigInteger().compareTo(BigInteger.ZERO) > 0) {
                    // Quantity has been increased for an existing material portion, need to check if the additional quantity amount is available
                    lotQuantitiesToCheck.put(lotId, difference);
                }
            } else {
                // Portion(s) from a new lot has been added, check if the total quantity amount is available.
                lotQuantitiesToCheck.put(lotId, quantity);
            }
        });

        if (lotQuantitiesToCheck.size() > 0) {
            Map<Long, Boolean> quantityCheckresult = this.areQuantitiesAvailable(lotQuantitiesToCheck);

            Set<Long> lotIdsWithUnavailableQuantity = quantityCheckresult.entrySet().stream().filter(entry -> entry.getValue() == false).map(Map.Entry::getKey).collect(Collectors.toSet());
            if (!lotIdsWithUnavailableQuantity.isEmpty()) {
                throw new MaterialLotQuantityNotAvailableException(lotIdsWithUnavailableQuantity);
            }
        }

        final List<MixtureMaterialPortion> updated = this.updateService.getPatchEntities(existingEntities, patchMaterialPortions);

        return this.repoService.saveAll(updated);
    }

    @Override
    public long deleteMaterialPortions(Set<Long> materialPortionIds) {
        return this.repoService.delete(materialPortionIds);
    }

    @Override
    public boolean materialPortionExists(Long materialPortionId) {
        return this.repoService.exists(materialPortionId);
    }
}

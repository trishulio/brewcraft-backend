package io.company.brewcraft.service;

import static io.company.brewcraft.repository.RepositoryUtil.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.measure.Quantity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import io.company.brewcraft.model.BaseMaterialPortion;
import io.company.brewcraft.model.Lot;
import io.company.brewcraft.model.MaterialLot;
import io.company.brewcraft.model.MaterialPortion;
import io.company.brewcraft.model.Mixture;
import io.company.brewcraft.model.StockLot;
import io.company.brewcraft.model.UpdateMaterialPortion;
import io.company.brewcraft.repository.MaterialPortionRepository;
import io.company.brewcraft.repository.SpecificationBuilder;
import io.company.brewcraft.service.exception.EntityNotFoundException;
import io.company.brewcraft.service.exception.MaterialLotQuantityNotAvailableException;
import io.company.brewcraft.util.QuantityCalculator;
import io.company.brewcraft.util.validator.Validator;

@Transactional
public class MaterialPortionServiceImpl extends BaseService implements MaterialPortionService {
    private static final Logger log = LoggerFactory.getLogger(MaterialPortionServiceImpl.class);
    
    private MaterialPortionRepository materialPortionRepository;
    
    private LotAggregationService lotAggredationService;

    public MaterialPortionServiceImpl(MaterialPortionRepository materialPortionRepository, LotAggregationService lotAggredationService) {
        this.materialPortionRepository = materialPortionRepository;
        this.lotAggredationService = lotAggredationService;
    }

    @Override
    public Page<MaterialPortion> getMaterialPortions(Set<Long> ids, Set<Long> mixtureIds, Set<Long> materialLotIds, int page, int size, SortedSet<String> sort, boolean orderAscending) {
        Specification<MaterialPortion> spec = SpecificationBuilder.builder()
                                                                  .in(MaterialPortion.FIELD_ID, ids)
                                                                  .in(new String[] {MaterialPortion.FIELD_MIXTURE, Mixture.FIELD_ID}, mixtureIds)
                                                                  .in(new String[] {MaterialPortion.MATERIAL_LOT, MaterialLot.FIELD_ID}, materialLotIds)
                                                                  .build();

            Page<MaterialPortion> materialPortionsPage = materialPortionRepository.findAll(spec, pageRequest(sort, orderAscending, page, size));

            return materialPortionsPage;
    }

    @Override
    public MaterialPortion getMaterialPortion(Long materialPortionId) {
        MaterialPortion materialPortion = materialPortionRepository.findById(materialPortionId).orElse(null);

        return materialPortion;
    }

    @Override
    public List<MaterialPortion> addMaterialPortions(List<MaterialPortion> materialPortions) {
        materialPortionRepository.refresh(materialPortions);
        
        materialPortions.forEach(materialPortion -> {
            Validator.assertion(materialPortion.getQuantity().getValue().doubleValue() > 0, RuntimeException.class, "Quantities must be greater than 0");   
        });
        
        Map<Long, Quantity<?>> lotToQuantity = materialPortions.stream().collect(Collectors.toMap(MaterialPortion::getId, MaterialPortion::getQuantity));
        Map<Long, Boolean> quantityCheckresult = areQuantitiesAvailable(lotToQuantity);
        
        Set<Long> lotIdsWithUnavailableQuantity = quantityCheckresult.entrySet().stream().filter(entry -> entry.getValue() == false).map(Map.Entry::getKey).collect(Collectors.toSet());
        
        if (!lotIdsWithUnavailableQuantity.isEmpty()) {
            throw new MaterialLotQuantityNotAvailableException(lotIdsWithUnavailableQuantity);
        }

        List<MaterialPortion> addedMaterialPortions = materialPortionRepository.saveAll(materialPortions);
        materialPortionRepository.flush();

        return addedMaterialPortions;
    }

    @Override
    public MaterialPortion addMaterialPortion(MaterialPortion materialPortion) {
        return this.addMaterialPortions(List.of(materialPortion)).get(0);
    }

    @Override
    public MaterialPortion putMaterialPortion(Long materialPortionId, MaterialPortion putMaterialPortion) {
        materialPortionRepository.refresh(List.of(putMaterialPortion));
        
        Validator.assertion(putMaterialPortion.getQuantity().getValue().doubleValue() > 0, RuntimeException.class, "Quantity must be greater than 0");

        MaterialPortion existingMaterialPortion = getMaterialPortion(materialPortionId);

        if (existingMaterialPortion == null) {
            existingMaterialPortion = putMaterialPortion;
            existingMaterialPortion.setId(materialPortionId);
            
            Map<Long, Boolean> quantityCheckresult = areQuantitiesAvailable(Map.of(existingMaterialPortion.getMaterialLot().getId(), existingMaterialPortion.getQuantity()));
            
            Set<Long> lotIdsWithUnavailableQuantity = quantityCheckresult.entrySet().stream().filter(entry -> entry.getValue() == false).map(Map.Entry::getKey).collect(Collectors.toSet());
            
            if (!lotIdsWithUnavailableQuantity.isEmpty()) {
                throw new MaterialLotQuantityNotAvailableException(lotIdsWithUnavailableQuantity);
            }    
        } else {
            existingMaterialPortion.optimisticLockCheck(putMaterialPortion);
            
            boolean isPutOnSameLot = putMaterialPortion.getMaterialLot().getId() == existingMaterialPortion.getMaterialLot().getId();

            if (isPutOnSameLot) {
                Quantity<?> patchedQuantityDelta = QuantityCalculator.subtract(putMaterialPortion.getQuantity(), existingMaterialPortion.getQuantity());
                
                Map<Long, Boolean> quantityCheckresult = areQuantitiesAvailable(Map.of(existingMaterialPortion.getMaterialLot().getId(), patchedQuantityDelta));
                
                Set<Long> lotIdsWithUnavailableQuantity = quantityCheckresult.entrySet().stream().filter(entry -> entry.getValue() == false).map(Map.Entry::getKey).collect(Collectors.toSet());
                
                if (patchedQuantityDelta.getValue().doubleValue() > 0 && !lotIdsWithUnavailableQuantity.isEmpty()) {
                    throw new MaterialLotQuantityNotAvailableException(lotIdsWithUnavailableQuantity);
                }
            } else {  
                Map<Long, Boolean> quantityCheckresult = areQuantitiesAvailable(Map.of(putMaterialPortion.getMaterialLot().getId(), putMaterialPortion.getQuantity()));
                
                Set<Long> lotIdsWithUnavailableQuantity = quantityCheckresult.entrySet().stream().filter(entry -> entry.getValue() == false).map(Map.Entry::getKey).collect(Collectors.toSet());
                
                if (!lotIdsWithUnavailableQuantity.isEmpty()) {
                    throw new MaterialLotQuantityNotAvailableException(lotIdsWithUnavailableQuantity);
                }
            }
            
            existingMaterialPortion.override(putMaterialPortion, getPropertyNames(BaseMaterialPortion.class));
        }

        return materialPortionRepository.saveAndFlush(existingMaterialPortion);
    }

    @Override
    public MaterialPortion patchMaterialPortion(Long materialPortionId, MaterialPortion patchMaterialPortion) {
        MaterialPortion existingMaterialPortion = Optional.ofNullable(getMaterialPortion(materialPortionId)).orElseThrow(() -> new EntityNotFoundException("MaterialPortion", materialPortionId.toString()));

        materialPortionRepository.refresh(List.of(existingMaterialPortion));
        
        if (patchMaterialPortion.getQuantity() != null) {
            Validator.assertion(patchMaterialPortion.getQuantity().getValue().doubleValue() > 0, RuntimeException.class, "Quantity must be greater than 0");
        }

        existingMaterialPortion.optimisticLockCheck(patchMaterialPortion);
                
        boolean isPatchOnSameLot = patchMaterialPortion.getMaterialLot() == null || patchMaterialPortion.getMaterialLot().getId() == null 
                || (patchMaterialPortion.getMaterialLot() != null && patchMaterialPortion.getMaterialLot().getId() == existingMaterialPortion.getMaterialLot().getId());
        
        if (isPatchOnSameLot && patchMaterialPortion.getQuantity() != null) {
            Quantity<?> patchedQuantityDelta = QuantityCalculator.subtract(patchMaterialPortion.getQuantity(), existingMaterialPortion.getQuantity());
            
            Map<Long, Boolean> quantityCheckresult = areQuantitiesAvailable(Map.of(existingMaterialPortion.getMaterialLot().getId(), patchedQuantityDelta));
            
            Set<Long> lotIdsWithUnavailableQuantity = quantityCheckresult.entrySet().stream().filter(entry -> entry.getValue() == false).map(Map.Entry::getKey).collect(Collectors.toSet());
            
            if (patchedQuantityDelta.getValue().doubleValue() > 0 && !lotIdsWithUnavailableQuantity.isEmpty()) {
                throw new MaterialLotQuantityNotAvailableException(lotIdsWithUnavailableQuantity);
            }
        }
        
        if (!isPatchOnSameLot) {
            Quantity<?> quantity = patchMaterialPortion.getQuantity() != null ? patchMaterialPortion.getQuantity() : existingMaterialPortion.getQuantity();
            
            Map<Long, Boolean> quantityCheckresult = areQuantitiesAvailable(Map.of(existingMaterialPortion.getMaterialLot().getId(), quantity));
            
            Set<Long> lotIdsWithUnavailableQuantity = quantityCheckresult.entrySet().stream().filter(entry -> entry.getValue() == false).map(Map.Entry::getKey).collect(Collectors.toSet());
            
            if (!lotIdsWithUnavailableQuantity.isEmpty()) {
                throw new MaterialLotQuantityNotAvailableException(lotIdsWithUnavailableQuantity);
            }   
        }
         
        existingMaterialPortion.outerJoin(patchMaterialPortion, getPropertyNames(UpdateMaterialPortion.class)); 
        
        return materialPortionRepository.saveAndFlush(existingMaterialPortion);
    }

    @Override
    public void deleteMaterialPortion(Long materialPortionId) {
        materialPortionRepository.deleteById(materialPortionId);
    }

    @Override
    public boolean materialPortionExists(Long materialPortionId) {
        return materialPortionRepository.existsById(materialPortionId);
    }  
    
    private Map<Long, Boolean> areQuantitiesAvailable(Map<Long, Quantity<?>> lotIdToQuantity) {
        Map<Long, Boolean> result = new HashMap<>();
        lotIdToQuantity.forEach((lotId, quantity) -> result.put(lotId, false)); //init result map to false for all lot ids
        
        List<StockLot> stockLots = lotAggredationService.getAggregatedStockQuantity(lotIdToQuantity.keySet(), null, null, null, null, null, null, null, null, AggregationFunction.SUM, new Lot.AggregationField[] { Lot.AggregationField.MATERIAL }, new TreeSet<>(List.of("id")), true, 0, Integer.MAX_VALUE).getContent();
        
        if (stockLots != null && !stockLots.isEmpty()) {
            stockLots.forEach(stockLot -> {
                Quantity<?> requestedQuantity = lotIdToQuantity.get(stockLot.getId());
                
                Validator.assertion(requestedQuantity.getUnit().isCompatible(stockLot.getQuantity().getUnit()), RuntimeException.class, "Requested quantity unit is incompatible with material lot unit");            
                
                Quantity<?> availableQuantity = stockLot.getQuantity();
                
                BigDecimal remainingQuantityValue = new BigDecimal(QuantityCalculator.subtract(availableQuantity, requestedQuantity).getValue().toString());
                
                //Requested quantity is only available if remaining quantity is >= 0
                if (remainingQuantityValue.compareTo(BigDecimal.ZERO) == 0 || remainingQuantityValue.compareTo(BigDecimal.ZERO) > 0) {
                    result.put(stockLot.getId(), true);
                }
            });
        }
        
        return result;
    }

}

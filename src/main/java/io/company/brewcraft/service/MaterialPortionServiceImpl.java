package io.company.brewcraft.service;

import static io.company.brewcraft.repository.RepositoryUtil.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.SortedSet;
import java.util.stream.Collectors;

import javax.measure.Quantity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import io.company.brewcraft.model.BaseMaterialPortion;
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
    
    private StockLotService stockLotService;

    public MaterialPortionServiceImpl(MaterialPortionRepository materialPortionRepository, StockLotService stockLotService) {
        this.materialPortionRepository = materialPortionRepository;
        this.stockLotService = stockLotService;
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
            Validator.assertion(new BigDecimal(materialPortion.getQuantity().getValue().toString()).compareTo(BigDecimal.ZERO) > 0, RuntimeException.class, "Quantities must be greater than 0");   
        });
        
        Map<Long, Quantity<?>> lotToQuantity = materialPortions.stream().collect(Collectors.toMap(materialPortion -> materialPortion.getMaterialLot().getId(), MaterialPortion::getQuantity));
        Map<Long, Boolean> quantityCheckresult = this.areQuantitiesAvailable(lotToQuantity);
        
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
        
        Validator.assertion(new BigDecimal(putMaterialPortion.getQuantity().getValue().toString()).compareTo(BigDecimal.ZERO) > 0, RuntimeException.class, "Quantity must be greater than 0");

        MaterialPortion existingMaterialPortion = getMaterialPortion(materialPortionId);

        if (existingMaterialPortion == null) {
            existingMaterialPortion = putMaterialPortion;
            existingMaterialPortion.setId(materialPortionId);
            
            Boolean quantityAvailable = this.isQuantityAvailable(existingMaterialPortion.getMaterialLot().getId(), existingMaterialPortion.getQuantity());
                        
            if (!quantityAvailable) {
                throw new MaterialLotQuantityNotAvailableException(Set.of(existingMaterialPortion.getMaterialLot().getId()));
            }    
        } else {
            existingMaterialPortion.optimisticLockCheck(putMaterialPortion);
            
            boolean isPutOnSameLot = putMaterialPortion.getMaterialLot().getId() == existingMaterialPortion.getMaterialLot().getId();

            //If put is on the same lot, we only need to check if the difference in quantity is available
            Quantity<?> quantityToCheck = isPutOnSameLot ? QuantityCalculator.subtract(putMaterialPortion.getQuantity(), existingMaterialPortion.getQuantity()) : putMaterialPortion.getQuantity();
            
            if (new BigDecimal(quantityToCheck.getValue().toString()).compareTo(BigDecimal.ZERO) > 0) {
                Boolean quantityAvailable = this.isQuantityAvailable(putMaterialPortion.getMaterialLot().getId(), quantityToCheck);
            
                if (!quantityAvailable) {
                    throw new MaterialLotQuantityNotAvailableException(Set.of(putMaterialPortion.getMaterialLot().getId()));
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
            Validator.assertion(new BigDecimal(patchMaterialPortion.getQuantity().getValue().toString()).compareTo(BigDecimal.ZERO) > 0, RuntimeException.class, "Quantity must be greater than 0");
        }

        existingMaterialPortion.optimisticLockCheck(patchMaterialPortion);
                
        boolean isPatchOnSameLot = patchMaterialPortion.getMaterialLot() == null || patchMaterialPortion.getMaterialLot().getId() == null 
                || patchMaterialPortion.getMaterialLot().getId() == existingMaterialPortion.getMaterialLot().getId();       
           
        boolean isQuantityCheckRequired = (isPatchOnSameLot && patchMaterialPortion.getQuantity() != null) || !isPatchOnSameLot;
        
        if (isQuantityCheckRequired) {
            Long lotIdToCheck = isPatchOnSameLot ? existingMaterialPortion.getMaterialLot().getId() : patchMaterialPortion.getMaterialLot().getId();
            
            //If patch is on the same lot, we only need to check if the difference in quantity is available
            Quantity<?> quantityToCheck = isPatchOnSameLot ? QuantityCalculator.subtract(patchMaterialPortion.getQuantity(), existingMaterialPortion.getQuantity()) : 
                patchMaterialPortion.getQuantity() != null ? patchMaterialPortion.getQuantity() : existingMaterialPortion.getQuantity();

            if (new BigDecimal(quantityToCheck.getValue().toString()).compareTo(BigDecimal.ZERO) > 0) {
                Boolean quantityAvailable = this.isQuantityAvailable(lotIdToCheck, quantityToCheck);

                if (!quantityAvailable) {
                    throw new MaterialLotQuantityNotAvailableException(Set.of(lotIdToCheck));
                } 
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
    
    private Boolean isQuantityAvailable(Long stockLotId, Quantity<?> quantity) {
        Boolean result = false;
        
        Map<Long, Boolean> quantityCheckResult = this.areQuantitiesAvailable(Map.of(stockLotId, quantity));
        
        if(quantityCheckResult.get(stockLotId) == true) {
            result = true;
        }
        
        return result;        
    }

    private Map<Long, Boolean> areQuantitiesAvailable(Map<Long, Quantity<?>> lotIdToQuantity) {
        Map<Long, Boolean> result = new HashMap<>();
        lotIdToQuantity.forEach((lotId, quantity) -> result.put(lotId, false)); //init result map to false for all lot ids
        
        List<StockLot> stockLots = stockLotService.getAllByIds(lotIdToQuantity.keySet());
                        
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

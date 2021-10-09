package io.company.brewcraft.service;

import static io.company.brewcraft.repository.RepositoryUtil.*;

import java.math.BigDecimal;
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

import io.company.brewcraft.model.BaseMixtureMaterialPortion;
import io.company.brewcraft.model.MaterialLot;
import io.company.brewcraft.model.MaterialPortion;
import io.company.brewcraft.model.Mixture;
import io.company.brewcraft.model.MixtureMaterialPortion;
import io.company.brewcraft.model.UpdateMixtureMaterialPortion;
import io.company.brewcraft.repository.MixtureMaterialPortionRepository;
import io.company.brewcraft.repository.SpecificationBuilder;
import io.company.brewcraft.service.exception.EntityNotFoundException;
import io.company.brewcraft.service.exception.MaterialLotQuantityNotAvailableException;
import io.company.brewcraft.util.QuantityCalculator;
import io.company.brewcraft.util.validator.Validator;

@Transactional
public class MixtureMaterialPortionServiceImpl extends BaseMaterialPortionService implements MixtureMaterialPortionService {
    private static final Logger log = LoggerFactory.getLogger(MixtureMaterialPortionServiceImpl.class);
    
    private MixtureMaterialPortionRepository mixtureMaterialPortionRepository;
    
    public MixtureMaterialPortionServiceImpl(MixtureMaterialPortionRepository mixtureMaterialPortionRepository, StockLotService stockLotService) {
        super(stockLotService);
        this.mixtureMaterialPortionRepository = mixtureMaterialPortionRepository;
    }

    @Override
    public Page<MixtureMaterialPortion> getMaterialPortions(Set<Long> ids, Set<Long> mixtureIds, Set<Long> materialLotIds, int page, int size, SortedSet<String> sort, boolean orderAscending) {
        Specification<MixtureMaterialPortion> spec = SpecificationBuilder.builder()
                                                                  .in(MaterialPortion.FIELD_ID, ids)
                                                                  .in(new String[] {MixtureMaterialPortion.FIELD_MIXTURE, Mixture.FIELD_ID}, mixtureIds)
                                                                  .in(new String[] {MaterialPortion.MATERIAL_LOT, MaterialLot.FIELD_ID}, materialLotIds)
                                                                  .build();

            Page<MixtureMaterialPortion> materialPortionsPage = mixtureMaterialPortionRepository.findAll(spec, pageRequest(sort, orderAscending, page, size));

            return materialPortionsPage;
    }

    @Override
    public MixtureMaterialPortion getMaterialPortion(Long materialPortionId) {
        MixtureMaterialPortion materialPortion = mixtureMaterialPortionRepository.findById(materialPortionId).orElse(null);

        return materialPortion;
    }

    @Override
    public List<MixtureMaterialPortion> addMaterialPortions(List<MixtureMaterialPortion> materialPortions) {
        mixtureMaterialPortionRepository.refresh(materialPortions);
        
        materialPortions.forEach(materialPortion -> {
            Validator.assertion(new BigDecimal(materialPortion.getQuantity().getValue().toString()).compareTo(BigDecimal.ZERO) > 0, RuntimeException.class, "Quantities must be greater than 0");   
        });
        
        Map<Long, Quantity<?>> lotToQuantity = materialPortions.stream().collect(Collectors.toMap(materialPortion -> materialPortion.getMaterialLot().getId(), MixtureMaterialPortion::getQuantity));
        Map<Long, Boolean> quantityCheckresult = this.areQuantitiesAvailable(lotToQuantity);
        
        Set<Long> lotIdsWithUnavailableQuantity = quantityCheckresult.entrySet().stream().filter(entry -> entry.getValue() == false).map(Map.Entry::getKey).collect(Collectors.toSet());
        
        if (!lotIdsWithUnavailableQuantity.isEmpty()) {
            throw new MaterialLotQuantityNotAvailableException(lotIdsWithUnavailableQuantity);
        }

        List<MixtureMaterialPortion> addedMaterialPortions = mixtureMaterialPortionRepository.saveAll(materialPortions);
        mixtureMaterialPortionRepository.flush();

        return addedMaterialPortions;
    }

    @Override
    public MixtureMaterialPortion addMaterialPortion(MixtureMaterialPortion materialPortion) {
        return this.addMaterialPortions(List.of(materialPortion)).get(0);
    }

    @Override
    public MixtureMaterialPortion putMaterialPortion(Long materialPortionId, MixtureMaterialPortion putMaterialPortion) {
        mixtureMaterialPortionRepository.refresh(List.of(putMaterialPortion));
        
        Validator.assertion(new BigDecimal(putMaterialPortion.getQuantity().getValue().toString()).compareTo(BigDecimal.ZERO) > 0, RuntimeException.class, "Quantity must be greater than 0");

        MixtureMaterialPortion existingMaterialPortion = getMaterialPortion(materialPortionId);

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
            
            existingMaterialPortion.override(putMaterialPortion, getPropertyNames(BaseMixtureMaterialPortion.class));
        }
        
        return mixtureMaterialPortionRepository.saveAndFlush(existingMaterialPortion);
    }

    @Override
    public MixtureMaterialPortion patchMaterialPortion(Long materialPortionId, MixtureMaterialPortion patchMaterialPortion) {
        MixtureMaterialPortion existingMaterialPortion = Optional.ofNullable(getMaterialPortion(materialPortionId)).orElseThrow(() -> new EntityNotFoundException("MaterialPortion", materialPortionId.toString()));

        mixtureMaterialPortionRepository.refresh(List.of(existingMaterialPortion));
        
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
         
        existingMaterialPortion.outerJoin(patchMaterialPortion, getPropertyNames(UpdateMixtureMaterialPortion.class)); 
        
        return mixtureMaterialPortionRepository.saveAndFlush(existingMaterialPortion);
    }

    @Override
    public void deleteMaterialPortion(Long materialPortionId) {
        mixtureMaterialPortionRepository.deleteById(materialPortionId);
    }

    @Override
    public boolean materialPortionExists(Long materialPortionId) {
        return mixtureMaterialPortionRepository.existsById(materialPortionId);
    }  
    
}

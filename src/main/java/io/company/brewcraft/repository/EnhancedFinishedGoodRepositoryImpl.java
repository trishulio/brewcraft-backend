package io.company.brewcraft.repository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import io.company.brewcraft.model.FinishedGood;
import io.company.brewcraft.model.FinishedGoodMaterialPortion;
import io.company.brewcraft.model.FinishedGoodMixturePortion;
import io.company.brewcraft.service.FinishedGoodAccessor;

public class EnhancedFinishedGoodRepositoryImpl implements EnhancedFinishedGoodRepository {
    private static final Logger log = LoggerFactory.getLogger(EnhancedFinishedGoodRepositoryImpl.class);

    private final AccessorRefresher<Long, FinishedGoodAccessor, FinishedGood> refresher;

    private final SkuRepository skuRepository;
   
    private final FinishedGoodMixturePortionRepository fgMixturePortionRepository;
    
    private final FinishedGoodMaterialPortionRepository fgMaterialPortionRepository;

    @Autowired
    public EnhancedFinishedGoodRepositoryImpl(AccessorRefresher<Long, FinishedGoodAccessor, FinishedGood> refresher, SkuRepository skuRepository, FinishedGoodMixturePortionRepository fgMixturePortionRepository, FinishedGoodMaterialPortionRepository fgMaterialPortionRepository) {
        this.refresher = refresher;
        this.skuRepository = skuRepository;
        this.fgMixturePortionRepository = fgMixturePortionRepository;
        this.fgMaterialPortionRepository = fgMaterialPortionRepository;
    }

    @Override
    public void refresh(Collection<FinishedGood> finishedGoods) {
        this.skuRepository.refreshAccessors(finishedGoods);

        final List<FinishedGoodMixturePortion> mixturePortions = finishedGoods.stream().filter(i -> i.getMixturePortions() != null).flatMap(i -> i.getMixturePortions().stream()).collect(Collectors.toList());
        this.fgMixturePortionRepository.refresh(mixturePortions);
        
        final List<FinishedGoodMaterialPortion> materialPortions = finishedGoods.stream().filter(i -> i.getMaterialPortions() != null).flatMap(i -> i.getMaterialPortions().stream()).collect(Collectors.toList());
        this.fgMaterialPortionRepository.refresh(materialPortions);
    }

    @Override
    public void refreshAccessors(Collection<? extends FinishedGoodAccessor> accessors) {
        this.refresher.refreshAccessors(accessors);
    }
}

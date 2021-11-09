package io.company.brewcraft.service;

import java.util.List;
import java.util.Set;
import java.util.SortedSet;

import org.springframework.data.domain.Page;

import io.company.brewcraft.model.BaseMixtureMaterialPortion;
import io.company.brewcraft.model.MixtureMaterialPortion;
import io.company.brewcraft.model.UpdateMixtureMaterialPortion;

public interface MixtureMaterialPortionService {

    public Page<MixtureMaterialPortion> getMaterialPortions(Set<Long> ids, Set<Long> mixtureIds, Set<Long> materialLotIds, Set<Long> brewStageIds, Set<Long> brewIds, int page, int size, SortedSet<String> sort, boolean orderAscending);

    public MixtureMaterialPortion getMaterialPortion(Long materialPortionId);

    public List<MixtureMaterialPortion> addMaterialPortions(List<BaseMixtureMaterialPortion> materialPortions);

    public MixtureMaterialPortion addMaterialPortion(BaseMixtureMaterialPortion materialPortion);

    public List<MixtureMaterialPortion> putMaterialPortions(List<UpdateMixtureMaterialPortion> materialPortion);

    public List<MixtureMaterialPortion> patchMaterialPortions(List<UpdateMixtureMaterialPortion> materialPortion);

    public int deleteMaterialPortions(Set<Long> materialPortionIds);

    public boolean materialPortionExists(Long materialPortionId);

 }


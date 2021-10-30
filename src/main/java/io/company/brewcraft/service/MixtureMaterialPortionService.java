package io.company.brewcraft.service;

import java.util.List;
import java.util.Set;
import java.util.SortedSet;

import org.springframework.data.domain.Page;

import io.company.brewcraft.model.MixtureMaterialPortion;

public interface MixtureMaterialPortionService {

    public Page<MixtureMaterialPortion> getMaterialPortions(Set<Long> ids, Set<Long> mixtureIds, Set<Long> materialLotIds, Set<Long> brewStageIds, Set<Long> brewIds, int page, int size, SortedSet<String> sort, boolean orderAscending);

    public MixtureMaterialPortion getMaterialPortion(Long materialPortionId);

    public List<MixtureMaterialPortion> addMaterialPortions(List<MixtureMaterialPortion> materialPortions);

    public MixtureMaterialPortion addMaterialPortion(MixtureMaterialPortion materialPortion);

    public MixtureMaterialPortion putMaterialPortion(Long materialPortionId, MixtureMaterialPortion materialPortion);

    public MixtureMaterialPortion patchMaterialPortion(Long materialPortionId, MixtureMaterialPortion materialPortion);

    public void deleteMaterialPortion(Long materialPortionId);

    public boolean materialPortionExists(Long materialPortionId);

 }


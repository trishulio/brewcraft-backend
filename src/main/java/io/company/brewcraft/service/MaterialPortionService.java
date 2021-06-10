package io.company.brewcraft.service;

import java.util.List;
import java.util.Set;
import java.util.SortedSet;

import org.springframework.data.domain.Page;

import io.company.brewcraft.model.MaterialPortion;

public interface MaterialPortionService {

    public Page<MaterialPortion> getMaterialPortions(Set<Long> ids, Set<Long> mixtureIds, int page, int size, SortedSet<String> sort, boolean orderAscending);
    
    public MaterialPortion getMaterialPortion(Long materialPortionId);
    
    public List<MaterialPortion> addMaterialPortions(List<MaterialPortion> materialPortions, Long mixtureId);
    
    public MaterialPortion addMaterialPortion(MaterialPortion materialPortion, Long mixtureId);
    
    public MaterialPortion putMaterialPortion(Long materialPortionId, MaterialPortion materialPortion, Long mixtureId);
                
    public MaterialPortion patchMaterialPortion(Long materialPortionId, MaterialPortion materialPortion);
                
    public void deleteMaterialPortion(Long materialPortionId);
    
    public boolean materialPortionExists(Long materialPortionId);

 }


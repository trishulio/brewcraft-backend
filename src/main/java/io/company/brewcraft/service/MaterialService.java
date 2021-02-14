package io.company.brewcraft.service;

import java.util.Set;

import org.springframework.data.domain.Page;

import io.company.brewcraft.dto.UpdateMaterial;
import io.company.brewcraft.pojo.Material;

public interface MaterialService {

    public Page<Material> getMaterials(Set<Long> ids, Set<Long> categoryIds, Set<String> categoryNames, int page, int size, Set<String> sort, boolean orderAscending);
    
    public Material getMaterial(Long materialId);

    public Material addMaterial(Material material);
    
    public Material addMaterial(Material material, Long categoryId, String quantityUnitSymbol);        
        
    public Material putMaterial(Long materialId, UpdateMaterial material);
    
    public Material putMaterial(Long materialId, UpdateMaterial material, Long categoryId, String quantityUnitSymbol);
    
    public Material patchMaterial(Long materialId, UpdateMaterial material);
    
    public Material patchMaterial(Long materialId, UpdateMaterial material, Long categoryId, String quantityUnitSymbol);

    public void deleteMaterial(Long materialId);
    
    public boolean materialExists(Long materialId);
    
 }

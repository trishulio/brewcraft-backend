package io.company.brewcraft.repository;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import io.company.brewcraft.model.Material;
import io.company.brewcraft.service.MaterialAccessor;
import io.company.brewcraft.service.exception.EntityNotFoundException;

public class EnhancedMaterialRepositoryImpl implements EnhancedMaterialRepository {
    private static final Logger log = LoggerFactory.getLogger(EnhancedMaterialRepositoryImpl.class);

    private MaterialRepository materialRepo;

    @Autowired
    public EnhancedMaterialRepositoryImpl(MaterialRepository materialRepo) {
        this.materialRepo = materialRepo;
    }

    @Override
    public void refreshAccessors(Collection<? extends MaterialAccessor> accessors) {
        if (accessors != null && accessors.size() > 0) {
            Map<Long, List<MaterialAccessor>> lookupAccessorsByMaterialId = accessors.stream().filter(accessor -> accessor != null && accessor.getMaterial() != null).collect(Collectors.groupingBy(accessor -> accessor.getMaterial().getId()));

            List<Material> materials = materialRepo.findAllById(lookupAccessorsByMaterialId.keySet());

            if (lookupAccessorsByMaterialId.keySet().size() != materials.size()) {
                List<Long> materialIds = materials.stream().map(material -> material.getId()).collect(Collectors.toList());
                throw new EntityNotFoundException(String.format("Cannot find all materials in Id-Set: %s. Materials found with Ids: %s", lookupAccessorsByMaterialId.keySet(), materialIds));
            }

            accessors.forEach(i -> i.setMaterial((Material) null));
            materials.forEach(material -> lookupAccessorsByMaterialId.get(material.getId()).forEach(item -> item.setMaterial(material)));
        }
    }
}

package io.company.brewcraft.repository;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.company.brewcraft.model.ShipmentStatus;
import io.company.brewcraft.service.ShipmentStatusAccessor;
import io.company.brewcraft.service.exception.EntityNotFoundException;

public class EnhancedShipmentStatusRepositoryImpl implements EnhancedShipmentStatusRepository {
    private static final Logger log = LoggerFactory.getLogger(EnhancedShipmentStatusRepositoryImpl.class);
    
    private ShipmentStatusRepository statusRepo;

    public EnhancedShipmentStatusRepositoryImpl(ShipmentStatusRepository statusRepo) {
        this.statusRepo = statusRepo;
    }

    @Override
    public void refreshAccessors(Collection<? extends ShipmentStatusAccessor> accessors) {
        if (accessors != null && accessors.size() > 0) {
            Map<String, List<ShipmentStatusAccessor>> lookupAccessorByStatusName = accessors.stream().filter(accessor -> accessor != null && accessor.getStatus() != null).collect(Collectors.groupingBy(accessor -> accessor.getStatus().getName()));

            Collection<ShipmentStatus> statuses = statusRepo.findByNames(lookupAccessorByStatusName.keySet());

            if (lookupAccessorByStatusName.keySet().size() != statuses.size()) {
                List<String> statusNames = statuses.stream().map(status -> status.getName()).collect(Collectors.toList());
                throw new EntityNotFoundException(String.format("Cannot find all statuses in Name-Set: %s. ShipmentStatuses found with names: %s", lookupAccessorByStatusName.keySet(), statusNames));
            }

            accessors.forEach(i -> i.setStatus(null));
            statuses.forEach(status -> lookupAccessorByStatusName.get(status.getName()).forEach(accessor -> accessor.setStatus(status)));
        }
    }
}

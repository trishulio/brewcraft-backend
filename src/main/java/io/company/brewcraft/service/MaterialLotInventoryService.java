package io.company.brewcraft.service;

import static io.company.brewcraft.repository.RepositoryUtil.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Set;
import java.util.SortedSet;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import io.company.brewcraft.model.Material;
import io.company.brewcraft.model.MaterialLot;
import io.company.brewcraft.model.Shipment;
import io.company.brewcraft.model.Storage;
import io.company.brewcraft.repository.MaterialLotAggregationRepository;
import io.company.brewcraft.repository.SpecificationBuilder;

public class MaterialLotInventoryService {
    
    private MaterialLotAggregationRepository repo;
    
    public MaterialLotInventoryService(MaterialLotAggregationRepository repo) {
        this.repo = repo;
    }

    public Page<MaterialLot> getAggregatedProcurementQuantity(
        Set<Long> ids,
        Set<Long> excludeIds,
        Set<String> lotNumbers,
        Set<Long> materialIds,
        Set<Long> shipmentIds,
        Set<Long> storageIds,
        Set<String> shipmentNumbers,
        LocalDateTime deliveredDateFrom,
        LocalDateTime deliveredDateTo,
        AggregationFunction aggrFn,
        MaterialLot.AggregationField[] groupBy,
        SortedSet<String> sort,
        boolean orderAscending,
        int page,
        int size
    ) {
        Specification<MaterialLot> spec = SpecificationBuilder.builder()
                                                              .in(MaterialLot.FIELD_ID, ids)
                                                              .not().in(MaterialLot.FIELD_ID, excludeIds)
                                                              .in(MaterialLot.FIELD_LOT_NUMBER, lotNumbers)
                                                              .in(new String[] { MaterialLot.FIELD_MATERIAL, Material.FIELD_ID }, materialIds)
                                                              .in(new String[] { MaterialLot.FIELD_SHIPMENT, Shipment.FIELD_ID }, shipmentIds)
                                                              .in(new String[] { MaterialLot.FIELD_STORAGE, Storage.FIELD_ID }, storageIds)
                                                              .in(new String[] { MaterialLot.FIELD_SHIPMENT, Shipment.FIELD_SHIPMENT_NUMBER }, shipmentNumbers)
                                                              .between(new String[] { MaterialLot.FIELD_SHIPMENT, Shipment.FIELD_DELIVERED_DATE }, deliveredDateFrom, deliveredDateTo)
                                                              .build();

        PageRequest pageable = pageRequest(sort, orderAscending, page, size);

        Selector selectAttr = new Selector();
        Selector groupByAttr = new Selector();
        
        Arrays.stream(groupBy).forEach(col -> {
            selectAttr.select(col);
            groupByAttr.select(col);
        });

        selectAttr.select(MaterialLot.AggregationField.QUANTITY_UNIT);
        groupByAttr.select(MaterialLot.AggregationField.QUANTITY_UNIT);

        selectAttr.select(aggrFn.getAggregation(MaterialLot.AggregationField.QUANTITY_VALUE));

        return repo.getAggregation(selectAttr, groupByAttr ,spec, pageable); 
    }
}

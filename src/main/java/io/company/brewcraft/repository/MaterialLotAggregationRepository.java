package io.company.brewcraft.repository;

import io.company.brewcraft.model.MaterialLot;

/***
 * This class must only be used as a wrapper around the AggregationRepository with specified
 * Entity type. All functions should be implemented in a generic way in the AggregationRepository
 * itself.
 */
public class MaterialLotAggregationRepository extends TypedAggregationRepository<MaterialLot> {

    public MaterialLotAggregationRepository(AggregationRepository repo) {
        super(repo, MaterialLot.class);
    }
}

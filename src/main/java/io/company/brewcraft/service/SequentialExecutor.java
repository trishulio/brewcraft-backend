package io.company.brewcraft.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.company.brewcraft.model.Identified;

public class SequentialExecutor<ID, Entity extends Identified<ID>, BaseEntity, UpdateEntity> implements IaasRepository<ID, Entity, BaseEntity, UpdateEntity> {
    private IaasClient<ID, Entity, BaseEntity, UpdateEntity> iaasClient;
    
    public SequentialExecutor(IaasClient<ID, Entity, BaseEntity, UpdateEntity> iaasClient) {
        this.iaasClient = iaasClient;
    }

    @Override
    public List<Entity> get(Set<ID> ids) {
        return ids.stream().map(this.iaasClient::get).toList();
    }

    @Override
    public <BE extends BaseEntity> List<Entity> add(List<BE> additions) {
        return additions.stream().map(this.iaasClient::add).toList();
    }

    @Override
    public <UE extends UpdateEntity> List<Entity> put(List<UE> updates) {
        return updates.stream().map(this.iaasClient::put).toList();
    }

    @Override
    public long delete(Set<ID> ids) {
        return ids.stream().map(this.iaasClient::delete).filter(isDeleted -> isDeleted).count();
    }

    @Override
    public Map<ID, Boolean> exists(Set<ID> ids) {
        Map<ID, Boolean> exists = new HashMap<>();

        this.get(ids)
             .stream()
             .map(Identified::getId)
             .forEach(existingId -> exists.put(existingId, true));
        ids.forEach(id -> exists.putIfAbsent(id, false));

        return exists;
    }
}

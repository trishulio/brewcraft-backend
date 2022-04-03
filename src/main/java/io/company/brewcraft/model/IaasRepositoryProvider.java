package io.company.brewcraft.model;

import io.company.brewcraft.service.IaasRepository;

public interface IaasRepositoryProvider<ID, Entity extends Identified<ID>, BaseEntity, UpdateEntity> {
    IaasRepository<ID, Entity, BaseEntity, UpdateEntity> getIaasRepository();
}

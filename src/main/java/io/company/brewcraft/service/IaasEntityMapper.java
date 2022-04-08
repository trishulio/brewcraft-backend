package io.company.brewcraft.service;

public interface IaasEntityMapper<IaasEntity, Entity> {
    Entity fromIaasEntity(IaasEntity entity);
}
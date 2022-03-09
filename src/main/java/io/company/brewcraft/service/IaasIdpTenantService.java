package io.company.brewcraft.service;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import io.company.brewcraft.model.BaseIaasIdpTenant;
import io.company.brewcraft.model.IaasIdpTenant;
import io.company.brewcraft.model.IaasIdpTenantAccessor;
import io.company.brewcraft.model.Identified;
import io.company.brewcraft.model.UpdateIaasIdpTenant;
import io.company.brewcraft.service.impl.IaasIdpTenantIaasRepository;

public class IaasIdpTenantService extends BaseService implements CrudService<String, IaasIdpTenant, BaseIaasIdpTenant, UpdateIaasIdpTenant, IaasIdpTenantAccessor> {
    private final IaasIdpTenantIaasRepository iaasRepo;

    private UpdateService<String, IaasIdpTenant, BaseIaasIdpTenant, UpdateIaasIdpTenant> updateService;

    public IaasIdpTenantService(UpdateService<String, IaasIdpTenant, BaseIaasIdpTenant, UpdateIaasIdpTenant> updateService, IaasIdpTenantIaasRepository iaasRepo) {
        this.updateService = updateService;
        this.iaasRepo = iaasRepo;
    }

    @Override
    public boolean exists(Set<String> ids) {
        return this.iaasRepo.get(ids).size() > 0;
    }

    @Override
    public boolean exist(String id) {
        return this.iaasRepo.get(Set.of(id)).size() > 0;
    }

    @Override
    public int delete(Set<String> ids) {
        this.iaasRepo.delete(ids);

        return ids.size();
    }

    @Override
    public int delete(String id) {
        this.iaasRepo.delete(Set.of(id));

        return 1;
    }

    @Override
    public IaasIdpTenant get(String id) {
        return this.iaasRepo.get(Set.of(id)).get(0);
    }

    public List<IaasIdpTenant> getAll(Set<String> ids) {
        return this.iaasRepo.get(ids);
    }

    @Override
    public List<IaasIdpTenant> getByIds(Collection<? extends Identified<String>> idProviders) {
        Set<String> ids = idProviders.stream()
                    .filter(provider -> provider != null)
                    .map(provider -> provider.getId())
                    .filter(id -> id != null)
                    .collect(Collectors.toSet());

        return this.iaasRepo.get(ids);
    }

    @Override
    public List<IaasIdpTenant> getByAccessorIds(Collection<? extends IaasIdpTenantAccessor> accessors) {
        List<IaasIdpTenant> idProviders = accessors.stream()
                                    .filter(accessor -> accessor != null)
                                    .map(accessor -> accessor.getIdpTenant())
                                    .filter(role -> role != null)
                                    .toList();
        return getByIds(idProviders);
    }

    @Override
    public List<IaasIdpTenant> add(List<BaseIaasIdpTenant> additions) {
        if (additions == null) {
            return null;
        }

        List<IaasIdpTenant> roles = this.updateService.getAddEntities(additions);

        return iaasRepo.add(roles);
    }

    @Override
    public List<IaasIdpTenant> put(List<UpdateIaasIdpTenant> updates) {
        if (updates == null) {
            return null;
        }

        List<IaasIdpTenant> existing = this.getByIds(updates);

        List<IaasIdpTenant> updated = this.updateService.getPutEntities(existing, updates);

        return iaasRepo.add(updated);
    }

    @Override
    public List<IaasIdpTenant> patch(List<UpdateIaasIdpTenant> updates) {
        if (updates == null) {
            return null;
        }

        List<IaasIdpTenant> existing = this.getByIds(updates);

        List<IaasIdpTenant> updated = this.updateService.getPatchEntities(existing, updates);

        return iaasRepo.add(updated);
    }
}

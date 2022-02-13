package io.company.brewcraft.service;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import io.company.brewcraft.model.BaseIaasPolicy;
import io.company.brewcraft.model.Identified;
import io.company.brewcraft.model.IaasPolicy;
import io.company.brewcraft.model.IaasPolicyAccessor;
import io.company.brewcraft.model.UpdateIaasPolicy;

@Transactional
public class IaasPolicyService extends BaseService implements CrudService<String, IaasPolicy, BaseIaasPolicy, UpdateIaasPolicy, IaasPolicyAccessor> {
    private final IaasPolicyIaasRepository iaasRepo;
    
    private UpdateService<String, IaasPolicy, BaseIaasPolicy, UpdateIaasPolicy> updateService;
    
    public IaasPolicyService(UpdateService<String, IaasPolicy, BaseIaasPolicy, UpdateIaasPolicy> updateService, IaasPolicyIaasRepository iaasRepo) {
        this.updateService = updateService;
        this.iaasRepo = iaasRepo;
    }

    @Override
    public boolean exists(Set<String> ids) {
        return this.iaasRepo.get(ids).size() > 0;
    }

    @Override
    public boolean exist(String id) {
        return this.iaasRepo.get(List.of(id)).size() > 0;
    }

    @Override
    public int delete(Set<String> ids) {
        this.iaasRepo.delete(ids);

        return ids.size();
    }

    @Override
    public int delete(String id) {
        this.iaasRepo.delete(List.of(id));
        
        return 1;
    }

    @Override
    public IaasPolicy get(String id) {
        return this.iaasRepo.get(List.of(id)).get(0);
    }
    
    public List<IaasPolicy> getAll(Set<String> ids) {
        return this.iaasRepo.get(ids);
    }

    @Override
    public List<IaasPolicy> getByIds(Collection<? extends Identified<String>> idProviders) {
        Set<String> ids = idProviders.stream()
                    .filter(provider -> provider != null)
                    .map(provider -> provider.getId())
                    .filter(id -> id != null)
                    .collect(Collectors.toSet());

        return this.iaasRepo.get(ids);
    }

    @Override
    public List<IaasPolicy> getByAccessorIds(Collection<? extends IaasPolicyAccessor> accessors) {
        List<IaasPolicy> idProviders = accessors.stream()
                                    .filter(accessor -> accessor != null)
                                    .map(accessor -> accessor.getIaasPolicy())
                                    .filter(policy -> policy != null)
                                    .toList();
        return getByIds(idProviders);
    }

    @Override
    public List<IaasPolicy> add(List<BaseIaasPolicy> additions) {
        if (additions == null) {
            return null;
        }

        List<IaasPolicy> policies = this.updateService.getAddEntities(additions);
        
        return iaasRepo.add(policies);
    }

    @Override
    public List<IaasPolicy> put(List<UpdateIaasPolicy> updates) {
        if (updates == null) {
            return null;
        }

        List<IaasPolicy> existing = this.getByIds(updates);
        
        List<IaasPolicy> updated = this.updateService.getPutEntities(existing, updates);

        return iaasRepo.add(updated);
    }

    @Override
    public List<IaasPolicy> patch(List<UpdateIaasPolicy> updates) {
        if (updates == null) {
            return null;
        }

        List<IaasPolicy> existing = this.getByIds(updates);
        
        List<IaasPolicy> updated = this.updateService.getPatchEntities(existing, updates);

        return iaasRepo.add(updated);
    }
}

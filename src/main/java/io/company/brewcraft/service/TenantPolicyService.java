package io.company.brewcraft.service;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import io.company.brewcraft.model.BaseTenantPolicy;
import io.company.brewcraft.model.Identified;
import io.company.brewcraft.model.TenantPolicy;
import io.company.brewcraft.model.TenantPolicyAccessor;
import io.company.brewcraft.model.UpdateTenantPolicy;

@Transactional
public class TenantPolicyService extends BaseService implements CrudService<Long, TenantPolicy, BaseTenantPolicy, UpdateTenantPolicy, TenantPolicyAccessor> {
    private final UpdateService<Long, TenantPolicy, BaseTenantPolicy, UpdateTenantPolicy> updateService;
    private final RepoService<Long, TenantPolicy, TenantPolicyAccessor> repoService;
    private final TenantPolicyIaasRepository iaasRepo;

    public TenantPolicyService(UpdateService<Long, TenantPolicy, BaseTenantPolicy, UpdateTenantPolicy> updateService, RepoService<Long, TenantPolicy, TenantPolicyAccessor> repoService, TenantPolicyIaasRepository iaasRepo) {
        this.updateService = updateService;
        this.repoService = repoService;
        this.iaasRepo = iaasRepo;
    }

    @Override
    public boolean exists(Set<Long> ids) {
        return this.repoService.exists(ids);
    }

    @Override
    public boolean exist(Long id) {
        return this.repoService.exists(id);
    }

    @Override
    public int delete(Set<Long> ids) {
        // TODO: Delete policies;
        return this.delete(ids);
    }

    @Override
    public int delete(Long id) {
        // TODO: Delete policies;
        return this.delete(id);
    }

    @Override
    public TenantPolicy get(Long id) {
        return this.repoService.get(id);
    }

    @Override
    public List<TenantPolicy> getByIds(Collection<? extends Identified<Long>> idProviders) {
        return this.repoService.getByIds(idProviders);
    }

    @Override
    public List<TenantPolicy> getByAccessorIds(Collection<? extends TenantPolicyAccessor> accessors) {
        return this.repoService.getByAccessorIds(accessors, accessor -> accessor.getTenantPolicy());
    }

    @Override
    public List<TenantPolicy> add(List<BaseTenantPolicy> additions) {
        if (additions == null) {
            return null;
        }

        final List<TenantPolicy> entities = this.updateService.getAddEntities(additions);
        List<TenantPolicy> policies = this.repoService.saveAll(entities);
        
        iaasRepo.putPolicies(policies);
        
        return policies;
    }

    @Override
    public List<TenantPolicy> put(List<UpdateTenantPolicy> updates) {
        if (updates == null) {
            return null;
        }

        final List<TenantPolicy> existing = this.repoService.getByIds(updates);
        final List<TenantPolicy> updated = this.updateService.getPutEntities(existing, updates);
        
        return this.repoService.saveAll(updated);
    }

    @Override
    public List<TenantPolicy> patch(List<UpdateTenantPolicy> updates) {
        if (updates == null) {
            return null;
        }

        final List<TenantPolicy> existing = this.repoService.getByIds(updates);
        final List<TenantPolicy> updated = this.updateService.getPatchEntities(existing, updates);

        return this.repoService.saveAll(updated);
    }

}

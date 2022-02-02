package io.company.brewcraft.service;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import io.company.brewcraft.model.BaseTenantIaasRole;
import io.company.brewcraft.model.Identified;
import io.company.brewcraft.model.TenantIaasRole;
import io.company.brewcraft.model.UpdateTenantIaasRole;

@Transactional
public class TenantIaasRoleService extends BaseService implements CrudService<Long, TenantIaasRole, BaseTenantIaasRole, UpdateTenantIaasRole, TenantIaasRoleAccessor> {
    private final UpdateService<Long, TenantIaasRole, BaseTenantIaasRole, UpdateTenantIaasRole> updateService;
    private final RepoService<Long, TenantIaasRole, TenantIaasRoleAccessor> repoService;
    private final TenantIaasRoleIaasRepository iaasRepo;

    public TenantIaasRoleService(UpdateService<Long, TenantIaasRole, BaseTenantIaasRole, UpdateTenantIaasRole> updateService, RepoService<Long, TenantIaasRole, TenantIaasRoleAccessor> repoService, TenantIaasRoleIaasRepository iaasRepo) {
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
        // TODO: Delete roles;
        return this.delete(ids);
    }

    @Override
    public int delete(Long id) {
        // TODO: Delete roles;
        return this.delete(id);
    }

    @Override
    public TenantIaasRole get(Long id) {
        return this.repoService.get(id);
    }

    @Override
    public List<TenantIaasRole> getByIds(Collection<? extends Identified<Long>> idProviders) {
        return this.repoService.getByIds(idProviders);
    }

    @Override
    public List<TenantIaasRole> getByAccessorIds(Collection<? extends TenantIaasRoleAccessor> accessors) {
        return this.repoService.getByAccessorIds(accessors, accessor -> accessor.getTenantRole());
    }

    @Override
    public List<TenantIaasRole> add(List<BaseTenantIaasRole> additions) {
        if (additions == null) {
            return null;
        }

        final List<TenantIaasRole> entities = this.updateService.getAddEntities(additions);
        List<TenantIaasRole> roles = this.repoService.saveAll(entities);
        
        iaasRepo.putRoles(roles);
        
        return roles;
    }

    @Override
    public List<TenantIaasRole> put(List<UpdateTenantIaasRole> updates) {
        if (updates == null) {
            return null;
        }

        final List<TenantIaasRole> existing = this.repoService.getByIds(updates);
        final List<TenantIaasRole> updated = this.updateService.getPutEntities(existing, updates);
        
        return this.repoService.saveAll(updated);
    }

    @Override
    public List<TenantIaasRole> patch(List<UpdateTenantIaasRole> updates) {
        if (updates == null) {
            return null;
        }

        final List<TenantIaasRole> existing = this.repoService.getByIds(updates);
        final List<TenantIaasRole> updated = this.updateService.getPatchEntities(existing, updates);

        return this.repoService.saveAll(updated);
    }

}

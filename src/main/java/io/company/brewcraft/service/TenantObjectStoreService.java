package io.company.brewcraft.service;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import io.company.brewcraft.model.BaseTenantObjectStore;
import io.company.brewcraft.model.Identified;
import io.company.brewcraft.model.TenantObjectStore;
import io.company.brewcraft.model.TenantObjectStoreAccessor;
import io.company.brewcraft.model.UpdateTenantObjectStore;

@Transactional
public class TenantObjectStoreService extends BaseService implements CrudService<Long, TenantObjectStore, BaseTenantObjectStore, UpdateTenantObjectStore, TenantObjectStoreAccessor> {
    private final UpdateService<Long, TenantObjectStore, BaseTenantObjectStore, UpdateTenantObjectStore> updateService;
    private final RepoService<Long, TenantObjectStore, TenantObjectStoreAccessor> repoService;
    private final TenantObjectStoreIaasRepository iaasRepo;

    public TenantObjectStoreService(UpdateService<Long, TenantObjectStore, BaseTenantObjectStore, UpdateTenantObjectStore> updateService, RepoService<Long, TenantObjectStore, TenantObjectStoreAccessor> repoService, TenantObjectStoreIaasRepository iaasRepo) {
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
        // TODO: Delete objectStores;
        return this.delete(ids);
    }

    @Override
    public int delete(Long id) {
        // TODO: Delete objectStores;
        return this.delete(id);
    }

    @Override
    public TenantObjectStore get(Long id) {
        return this.repoService.get(id);
    }

    @Override
    public List<TenantObjectStore> getByIds(Collection<? extends Identified<Long>> idProviders) {
        return this.repoService.getByIds(idProviders);
    }

    @Override
    public List<TenantObjectStore> getByAccessorIds(Collection<? extends TenantObjectStoreAccessor> accessors) {
        return this.repoService.getByAccessorIds(accessors, accessor -> accessor.getTenantObjectStoreAccesor());
    }

    @Override
    public List<TenantObjectStore> add(List<BaseTenantObjectStore> additions) {
        if (additions == null) {
            return null;
        }

        final List<TenantObjectStore> entities = this.updateService.getAddEntities(additions);
        List<TenantObjectStore> objectStores = this.repoService.saveAll(entities);
        
        iaasRepo.putObjectStores(objectStores);
        
        return objectStores;
    }

    @Override
    public List<TenantObjectStore> put(List<UpdateTenantObjectStore> updates) {
        if (updates == null) {
            return null;
        }

        final List<TenantObjectStore> existing = this.repoService.getByIds(updates);
        final List<TenantObjectStore> updated = this.updateService.getPutEntities(existing, updates);

        // TODO: URL shouldn't be updatable.
        
        return this.repoService.saveAll(updated);
    }

    @Override
    public List<TenantObjectStore> patch(List<UpdateTenantObjectStore> updates) {
        if (updates == null) {
            return null;
        }

        final List<TenantObjectStore> existing = this.repoService.getByIds(updates);
        final List<TenantObjectStore> updated = this.updateService.getPatchEntities(existing, updates);
        // TODO: URL shouldn't be updatable.

        return this.repoService.saveAll(updated);
    }

}

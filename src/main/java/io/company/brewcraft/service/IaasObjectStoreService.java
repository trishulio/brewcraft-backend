package io.company.brewcraft.service;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import io.company.brewcraft.model.BaseIaasObjectStore;
import io.company.brewcraft.model.Identified;
import io.company.brewcraft.model.IaasObjectStore;
import io.company.brewcraft.model.IaasObjectStoreAccessor;
import io.company.brewcraft.model.UpdateIaasObjectStore;

@Transactional
public class IaasObjectStoreService extends BaseService implements CrudService<String, IaasObjectStore, BaseIaasObjectStore, UpdateIaasObjectStore, IaasObjectStoreAccessor> {
   private final IaasObjectStoreIaasRepository iaasRepo;
    
    private UpdateService<String, IaasObjectStore, BaseIaasObjectStore, UpdateIaasObjectStore> updateService;
    
    public IaasObjectStoreService(UpdateService<String, IaasObjectStore, BaseIaasObjectStore, UpdateIaasObjectStore> updateService, IaasObjectStoreIaasRepository iaasRepo) {
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
    public IaasObjectStore get(String id) {
        return this.iaasRepo.get(List.of(id)).get(0);
    }
    
    public List<IaasObjectStore> getAll(Set<String> ids) {
        return this.iaasRepo.get(ids);
    }

    @Override
    public List<IaasObjectStore> getByIds(Collection<? extends Identified<String>> idProviders) {
        Set<String> ids = idProviders.stream()
                    .filter(provider -> provider != null)
                    .map(provider -> provider.getId())
                    .filter(id -> id != null)
                    .collect(Collectors.toSet());

        return this.iaasRepo.get(ids);
    }

    @Override
    public List<IaasObjectStore> getByAccessorIds(Collection<? extends IaasObjectStoreAccessor> accessors) {
        List<IaasObjectStore> idProviders = accessors.stream()
                                    .filter(accessor -> accessor != null)
                                    .map(accessor -> accessor.getIaasObjectStore())
                                    .filter(objectStore -> objectStore != null)
                                    .toList();
        return getByIds(idProviders);
    }

    @Override
    public List<IaasObjectStore> add(List<BaseIaasObjectStore> additions) {
        if (additions == null) {
            return null;
        }

        List<IaasObjectStore> objectStores = this.updateService.getAddEntities(additions);
        
        return iaasRepo.add(objectStores);
    }

    @Override
    public List<IaasObjectStore> put(List<UpdateIaasObjectStore> updates) {
        if (updates == null) {
            return null;
        }

        List<IaasObjectStore> existing = this.getByIds(updates);
        
        List<IaasObjectStore> updated = this.updateService.getPutEntities(existing, updates);

        return iaasRepo.add(updated);
    }

    @Override
    public List<IaasObjectStore> patch(List<UpdateIaasObjectStore> updates) {
        if (updates == null) {
            return null;
        }

        List<IaasObjectStore> existing = this.getByIds(updates);
        
        List<IaasObjectStore> updated = this.updateService.getPatchEntities(existing, updates);

        return iaasRepo.add(updated);
    }
}

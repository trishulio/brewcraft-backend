package io.company.brewcraft.service;

import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.company.brewcraft.model.BaseIaasObjectStoreFile;
import io.company.brewcraft.model.IaasObjectStoreFile;
import io.company.brewcraft.model.IaasObjectStoreFileAccessor;
import io.company.brewcraft.model.Identified;
import io.company.brewcraft.model.UpdateIaasObjectStoreFile;

@Transactional
public class IaasObjectStoreFileService extends BaseService implements CrudService<URI, IaasObjectStoreFile, BaseIaasObjectStoreFile, UpdateIaasObjectStoreFile, IaasObjectStoreFileAccessor> {
    private static final Logger log = LoggerFactory.getLogger(IaasObjectStoreFileService.class);

    private final IaasRepository<URI, IaasObjectStoreFile, BaseIaasObjectStoreFile, UpdateIaasObjectStoreFile> iaasRepo;

    private UpdateService<URI, IaasObjectStoreFile, BaseIaasObjectStoreFile, UpdateIaasObjectStoreFile> updateService;

    public IaasObjectStoreFileService(UpdateService<URI, IaasObjectStoreFile, BaseIaasObjectStoreFile, UpdateIaasObjectStoreFile> updateService, IaasRepository<URI, IaasObjectStoreFile, BaseIaasObjectStoreFile, UpdateIaasObjectStoreFile> iaasRepo) {
        this.updateService = updateService;
        this.iaasRepo = iaasRepo;
    }

    @Override
    public boolean exists(Set<URI> ids) {
        return iaasRepo.exists(ids).values()
                                    .stream().filter(b -> !b)
                                    .findAny()
                                    .orElseGet(() -> true);
    }

    @Override
    public boolean exist(URI id) {
        return exists(Set.of(id));
    }

    @Override
    public long delete(Set<URI> ids) {
        return this.iaasRepo.delete(ids);
    }

    @Override
    public long delete(URI id) {
        return this.iaasRepo.delete(Set.of(id));
    }

    @Override
    public IaasObjectStoreFile get(URI id) {
        IaasObjectStoreFile objectStore = null;

        List<IaasObjectStoreFile> policies = this.iaasRepo.get(Set.of(id));
        if (policies.size() == 1) {
            objectStore = policies.get(0);
        } else {
            log.debug("Get objectStore: '{}' returned {}", policies);
        }

        return objectStore;
    }

    public List<IaasObjectStoreFile> getAll(Set<URI> ids) {
        return this.iaasRepo.get(ids);
    }

    @Override
    public List<IaasObjectStoreFile> getByIds(Collection<? extends Identified<URI>> idProviders) {
        Set<URI> ids = idProviders.stream()
                    .filter(Objects::nonNull)
                    .map(provider -> provider.getId())
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());

        return this.iaasRepo.get(ids);
    }

    @Override
    public List<IaasObjectStoreFile> getByAccessorIds(Collection<? extends IaasObjectStoreFileAccessor> accessors) {
        List<IaasObjectStoreFile> idProviders = accessors.stream()
                                    .filter(Objects::nonNull)
                                    .map(accessor -> accessor.getObjectStoreFile())
                                    .filter(Objects::nonNull)
                                    .toList();
        return getByIds(idProviders);
    }

    @Override
    public List<IaasObjectStoreFile> add(List<BaseIaasObjectStoreFile> additions) {
        if (additions == null) {
            return null;
        }

        List<IaasObjectStoreFile> objectStores = this.updateService.getAddEntities(additions);

        return iaasRepo.add(objectStores);
    }

    @Override
    public List<IaasObjectStoreFile> put(List<UpdateIaasObjectStoreFile> updates) {
        if (updates == null) {
            return null;
        }

        List<IaasObjectStoreFile> updated = this.updateService.getPutEntities(null, updates);

        return iaasRepo.put(updated);
    }

    @Override
    public List<IaasObjectStoreFile> patch(List<UpdateIaasObjectStoreFile> updates) {
        if (updates == null) {
            return null;
        }

        throw new UnsupportedOperationException("Patch is not supported for file urls");
    }
}
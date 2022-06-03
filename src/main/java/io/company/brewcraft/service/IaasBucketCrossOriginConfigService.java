package io.company.brewcraft.service;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.company.brewcraft.model.IaasBucketCrossOriginConfiguration;
import io.company.brewcraft.model.IaasBucketCrossOriginConfigurationAccessor;
import io.company.brewcraft.model.Identified;

@Transactional
public class IaasBucketCrossOriginConfigService extends BaseService implements CrudService<String, IaasBucketCrossOriginConfiguration, IaasBucketCrossOriginConfiguration, IaasBucketCrossOriginConfiguration, IaasBucketCrossOriginConfigurationAccessor> {
    private static final Logger log = LoggerFactory.getLogger(IaasBucketCrossOriginConfigService.class);

    private final IaasRepository<String, IaasBucketCrossOriginConfiguration, IaasBucketCrossOriginConfiguration, IaasBucketCrossOriginConfiguration> iaasRepo;

    private UpdateService<String, IaasBucketCrossOriginConfiguration, IaasBucketCrossOriginConfiguration, IaasBucketCrossOriginConfiguration> updateService;

    public IaasBucketCrossOriginConfigService(UpdateService<String, IaasBucketCrossOriginConfiguration, IaasBucketCrossOriginConfiguration, IaasBucketCrossOriginConfiguration> updateService, IaasRepository<String, IaasBucketCrossOriginConfiguration, IaasBucketCrossOriginConfiguration, IaasBucketCrossOriginConfiguration> iaasRepo) {
        this.updateService = updateService;
        this.iaasRepo = iaasRepo;
    }

    @Override
    public boolean exists(Set<String> ids) {
        return iaasRepo.exists(ids).values()
                                    .stream().filter(b -> !b)
                                    .findAny()
                                    .orElseGet(() -> true);
    }

    @Override
    public boolean exist(String id) {
        return exists(Set.of(id));
    }

    @Override
    public long delete(Set<String> ids) {
        return this.iaasRepo.delete(ids);
    }

    @Override
    public long delete(String id) {
        return this.iaasRepo.delete(Set.of(id));
    }

    @Override
    public IaasBucketCrossOriginConfiguration get(String id) {
        IaasBucketCrossOriginConfiguration bucketCrossOriginConfig = null;

        List<IaasBucketCrossOriginConfiguration> bucketCrossOriginConfigs = this.iaasRepo.get(Set.of(id));
        if (bucketCrossOriginConfigs.size() == 1) {
            bucketCrossOriginConfig = bucketCrossOriginConfigs.get(0);
        } else {
            log.error("Unexpectedly returned more than 1 config for objectStore: {}: results {}", id, bucketCrossOriginConfigs);
        }

        return bucketCrossOriginConfig;
    }

    public List<IaasBucketCrossOriginConfiguration> getAll(Set<String> ids) {
        return this.iaasRepo.get(ids);
    }

    @Override
    public List<IaasBucketCrossOriginConfiguration> getByIds(Collection<? extends Identified<String>> idProviders) {
        Set<String> ids = idProviders.stream()
                    .filter(Objects::nonNull)
                    .map(provider -> provider.getId())
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());

        return this.iaasRepo.get(ids);
    }

    @Override
    public List<IaasBucketCrossOriginConfiguration> getByAccessorIds(Collection<? extends IaasBucketCrossOriginConfigurationAccessor> accessors) {
        List<IaasBucketCrossOriginConfiguration> idProviders = accessors.stream()
                                    .filter(Objects::nonNull)
                                    .map(accessor -> accessor.getIaasBucketCrossOriginConfiguration())
                                    .filter(Objects::nonNull)
                                    .toList();
        return getByIds(idProviders);
    }

    @Override
    public List<IaasBucketCrossOriginConfiguration> add(List<IaasBucketCrossOriginConfiguration> additions) {
        if (additions == null) {
            return null;
        }

        List<IaasBucketCrossOriginConfiguration> objectStoreCorsConfigs = this.updateService.getAddEntities(additions);

        return iaasRepo.add(objectStoreCorsConfigs);
    }

    @Override
    public List<IaasBucketCrossOriginConfiguration> put(List<IaasBucketCrossOriginConfiguration> updates) {
        if (updates == null) {
            return null;
        }

        List<IaasBucketCrossOriginConfiguration> updated = this.updateService.getPutEntities(null, updates);

        return iaasRepo.put(updated);
    }

    @Override
    public List<IaasBucketCrossOriginConfiguration> patch(List<IaasBucketCrossOriginConfiguration> updates) {
        if (updates == null) {
            return null;
        }

        List<IaasBucketCrossOriginConfiguration> existing = this.getByIds(updates);

        List<IaasBucketCrossOriginConfiguration> updated = this.updateService.getPatchEntities(existing, updates);

        return iaasRepo.put(updated);
    }
}
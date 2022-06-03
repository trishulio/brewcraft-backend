package io.company.brewcraft.service;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.company.brewcraft.model.IaasBucketCorsConfiguration;
import io.company.brewcraft.model.IaasBucketCorsConfigurationAccessor;
import io.company.brewcraft.model.Identified;

@Transactional
public class IaasBucketCorsConfigService extends BaseService implements CrudService<String, IaasBucketCorsConfiguration, IaasBucketCorsConfiguration, IaasBucketCorsConfiguration, IaasBucketCorsConfigurationAccessor> {
    private static final Logger log = LoggerFactory.getLogger(IaasBucketCorsConfigService.class);

    private final IaasRepository<String, IaasBucketCorsConfiguration, IaasBucketCorsConfiguration, IaasBucketCorsConfiguration> iaasRepo;

    private UpdateService<String, IaasBucketCorsConfiguration, IaasBucketCorsConfiguration, IaasBucketCorsConfiguration> updateService;

    public IaasBucketCorsConfigService(UpdateService<String, IaasBucketCorsConfiguration, IaasBucketCorsConfiguration, IaasBucketCorsConfiguration> updateService, IaasRepository<String, IaasBucketCorsConfiguration, IaasBucketCorsConfiguration, IaasBucketCorsConfiguration> iaasRepo) {
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
    public IaasBucketCorsConfiguration get(String id) {
        IaasBucketCorsConfiguration bucketCrossOriginConfig = null;

        List<IaasBucketCorsConfiguration> bucketCrossOriginConfigs = this.iaasRepo.get(Set.of(id));
        if (bucketCrossOriginConfigs.size() == 1) {
            bucketCrossOriginConfig = bucketCrossOriginConfigs.get(0);
        } else {
            log.error("Unexpectedly returned more than 1 config for objectStore: {}: results {}", id, bucketCrossOriginConfigs);
        }

        return bucketCrossOriginConfig;
    }

    public List<IaasBucketCorsConfiguration> getAll(Set<String> ids) {
        return this.iaasRepo.get(ids);
    }

    @Override
    public List<IaasBucketCorsConfiguration> getByIds(Collection<? extends Identified<String>> idProviders) {
        Set<String> ids = idProviders.stream()
                    .filter(Objects::nonNull)
                    .map(provider -> provider.getId())
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());

        return this.iaasRepo.get(ids);
    }

    @Override
    public List<IaasBucketCorsConfiguration> getByAccessorIds(Collection<? extends IaasBucketCorsConfigurationAccessor> accessors) {
        List<IaasBucketCorsConfiguration> idProviders = accessors.stream()
                                    .filter(Objects::nonNull)
                                    .map(accessor -> accessor.getIaasBucketCorsConfiguration())
                                    .filter(Objects::nonNull)
                                    .toList();
        return getByIds(idProviders);
    }

    @Override
    public List<IaasBucketCorsConfiguration> add(List<IaasBucketCorsConfiguration> additions) {
        if (additions == null) {
            return null;
        }

        List<IaasBucketCorsConfiguration> objectStoreCorsConfigs = this.updateService.getAddEntities(additions);

        return iaasRepo.add(objectStoreCorsConfigs);
    }

    @Override
    public List<IaasBucketCorsConfiguration> put(List<IaasBucketCorsConfiguration> updates) {
        if (updates == null) {
            return null;
        }

        List<IaasBucketCorsConfiguration> updated = this.updateService.getPutEntities(null, updates);

        return iaasRepo.put(updated);
    }

    @Override
    public List<IaasBucketCorsConfiguration> patch(List<IaasBucketCorsConfiguration> updates) {
        if (updates == null) {
            return null;
        }

        List<IaasBucketCorsConfiguration> existing = this.getByIds(updates);

        List<IaasBucketCorsConfiguration> updated = this.updateService.getPatchEntities(existing, updates);

        return iaasRepo.put(updated);
    }
}
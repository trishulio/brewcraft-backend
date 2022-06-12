package io.company.brewcraft.service;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.company.brewcraft.model.IaasPublicAccessBlock;
import io.company.brewcraft.model.IaasPublicAccessBlockAccessor;
import io.company.brewcraft.model.Identified;

@Transactional
public class IaasPublicAccessBlockService extends BaseService implements CrudService<String, IaasPublicAccessBlock, IaasPublicAccessBlock, IaasPublicAccessBlock, IaasPublicAccessBlockAccessor> {
    private static final Logger log = LoggerFactory.getLogger(IaasPublicAccessBlockService.class);

    private final IaasRepository<String, IaasPublicAccessBlock, IaasPublicAccessBlock, IaasPublicAccessBlock> iaasRepo;

    private UpdateService<String, IaasPublicAccessBlock, IaasPublicAccessBlock, IaasPublicAccessBlock> updateService;

    public IaasPublicAccessBlockService(UpdateService<String, IaasPublicAccessBlock, IaasPublicAccessBlock, IaasPublicAccessBlock> updateService, IaasRepository<String, IaasPublicAccessBlock, IaasPublicAccessBlock, IaasPublicAccessBlock> iaasRepo) {
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
    public IaasPublicAccessBlock get(String id) {
        IaasPublicAccessBlock bucketCrossOriginConfig = null;

        List<IaasPublicAccessBlock> publicAccessBlocks = this.iaasRepo.get(Set.of(id));
        if (publicAccessBlocks.size() == 1) {
            bucketCrossOriginConfig = publicAccessBlocks.get(0);
        } else {
            log.error("Unexpectedly returned more than 1 public access block for objectStore: {}: results {}", id, publicAccessBlocks);
        }

        return bucketCrossOriginConfig;
    }

    public List<IaasPublicAccessBlock> getAll(Set<String> ids) {
        return this.iaasRepo.get(ids);
    }

    @Override
    public List<IaasPublicAccessBlock> getByIds(Collection<? extends Identified<String>> idProviders) {
        Set<String> ids = idProviders.stream()
                    .filter(Objects::nonNull)
                    .map(provider -> provider.getId())
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());

        return this.iaasRepo.get(ids);
    }

    @Override
    public List<IaasPublicAccessBlock> getByAccessorIds(Collection<? extends IaasPublicAccessBlockAccessor> accessors) {
        List<IaasPublicAccessBlock> idProviders = accessors.stream()
                                    .filter(Objects::nonNull)
                                    .map(accessor -> accessor.getIaasPublicAccessBlock())
                                    .filter(Objects::nonNull)
                                    .toList();
        return getByIds(idProviders);
    }

    @Override
    public List<IaasPublicAccessBlock> add(List<IaasPublicAccessBlock> additions) {
        if (additions == null) {
            return null;
        }

        List<IaasPublicAccessBlock> publicAccessBlocks = this.updateService.getAddEntities(additions);

        return iaasRepo.add(publicAccessBlocks);
    }

    @Override
    public List<IaasPublicAccessBlock> put(List<IaasPublicAccessBlock> updates) {
        if (updates == null) {
            return null;
        }

        List<IaasPublicAccessBlock> updated = this.updateService.getPutEntities(null, updates);

        return iaasRepo.put(updated);
    }

    @Override
    public List<IaasPublicAccessBlock> patch(List<IaasPublicAccessBlock> updates) {
        if (updates == null) {
            return null;
        }

        List<IaasPublicAccessBlock> existing = this.getByIds(updates);

        List<IaasPublicAccessBlock> updated = this.updateService.getPatchEntities(existing, updates);

        return iaasRepo.put(updated);
    }
}
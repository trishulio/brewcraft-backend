package io.company.brewcraft.service;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import io.company.brewcraft.model.BaseIaasRolePolicyAttachment;
import io.company.brewcraft.model.IaasRolePolicyAttachment;
import io.company.brewcraft.model.IaasRolePolicyAttachmentAccessor;
import io.company.brewcraft.model.IaasRolePolicyAttachmentId;
import io.company.brewcraft.model.Identified;
import io.company.brewcraft.model.UpdateIaasRolePolicyAttachment;

@Transactional
public class IaasRolePolicyAttachmentService extends BaseService implements CrudService<IaasRolePolicyAttachmentId, IaasRolePolicyAttachment, BaseIaasRolePolicyAttachment, UpdateIaasRolePolicyAttachment, IaasRolePolicyAttachmentAccessor> {
   private final IaasRolePolicyAttachmentIaasRepository iaasRepo;

    private UpdateService<IaasRolePolicyAttachmentId, IaasRolePolicyAttachment, BaseIaasRolePolicyAttachment, UpdateIaasRolePolicyAttachment> updateService;

    public IaasRolePolicyAttachmentService(UpdateService<IaasRolePolicyAttachmentId, IaasRolePolicyAttachment, BaseIaasRolePolicyAttachment, UpdateIaasRolePolicyAttachment> updateService, IaasRolePolicyAttachmentIaasRepository iaasRepo) {
        this.updateService = updateService;
        this.iaasRepo = iaasRepo;
    }

    @Override
    public boolean exists(Set<IaasRolePolicyAttachmentId> ids) {
        return iaasRepo.exists(ids).values()
                                   .stream().filter(b -> !b)
                                   .findAny()
                                   .orElseGet(() -> true);
    }

    @Override
    public boolean exist(IaasRolePolicyAttachmentId id) {
        return iaasRepo.exists(id);
    }

    @Override
    public long delete(Set<IaasRolePolicyAttachmentId> ids) {
        return this.iaasRepo.delete(ids);
    }

    @Override
    public long delete(IaasRolePolicyAttachmentId id) {
        return this.iaasRepo.delete(Set.of(id));
    }

    @Override
    public IaasRolePolicyAttachment get(IaasRolePolicyAttachmentId id) {
        return this.iaasRepo.get(Set.of(id)).get(0);
    }

    public List<IaasRolePolicyAttachment> getAll(Set<IaasRolePolicyAttachmentId> ids) {
        return this.iaasRepo.get(ids);
    }

    @Override
    public List<IaasRolePolicyAttachment> getByIds(Collection<? extends Identified<IaasRolePolicyAttachmentId>> idProviders) {
        Set<IaasRolePolicyAttachmentId> ids = idProviders.stream()
                    .filter(Objects::nonNull)
                    .map(provider -> provider.getId())
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());

        return this.iaasRepo.get(ids);
    }

    @Override
    public List<IaasRolePolicyAttachment> getByAccessorIds(Collection<? extends IaasRolePolicyAttachmentAccessor> accessors) {
        List<IaasRolePolicyAttachment> idProviders = accessors.stream()
                                    .filter(Objects::nonNull)
                                    .map(accessor -> accessor.getIaasRolePolicyAttachment())
                                    .filter(Objects::nonNull)
                                    .toList();
        return getByIds(idProviders);
    }

    @Override
    public List<IaasRolePolicyAttachment> add(List<BaseIaasRolePolicyAttachment> additions) {
        if (additions == null) {
            return null;
        }

        List<IaasRolePolicyAttachment> rolePolicies = this.updateService.getAddEntities(additions);

        return iaasRepo.add(rolePolicies);
    }

    @Override
    public List<IaasRolePolicyAttachment> put(List<UpdateIaasRolePolicyAttachment> updates) {
        if (updates == null) {
            return null;
        }

        List<IaasRolePolicyAttachment> updated = this.updateService.getPutEntities(null, updates);

        return iaasRepo.put(updated);
    }

    @Override
    public List<IaasRolePolicyAttachment> patch(List<UpdateIaasRolePolicyAttachment> updates) {
        if (updates == null) {
            return null;
        }

        List<IaasRolePolicyAttachment> existing = this.getByIds(updates);

        List<IaasRolePolicyAttachment> updated = this.updateService.getPatchEntities(existing, updates);

        return iaasRepo.put(updated);
    }
}
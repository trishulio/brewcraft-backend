package io.company.brewcraft.service;

import java.util.Collection;
import java.util.List;
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
    public int delete(Set<IaasRolePolicyAttachmentId> ids) {
        this.iaasRepo.delete(ids);

        return ids.size();
    }

    @Override
    public int delete(IaasRolePolicyAttachmentId id) {
        this.iaasRepo.delete(Set.of(id));

        return 1;
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
                    .filter(provider -> provider != null)
                    .map(provider -> provider.getId())
                    .filter(id -> id != null)
                    .collect(Collectors.toSet());

        return this.iaasRepo.get(ids);
    }

    @Override
    public List<IaasRolePolicyAttachment> getByAccessorIds(Collection<? extends IaasRolePolicyAttachmentAccessor> accessors) {
        List<IaasRolePolicyAttachment> idProviders = accessors.stream()
                                    .filter(accessor -> accessor != null)
                                    .map(accessor -> accessor.getIaasRolePolicyAttachment())
                                    .filter(rolePolicy -> rolePolicy != null)
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

        List<IaasRolePolicyAttachment> existing = this.getByIds(updates);

        List<IaasRolePolicyAttachment> updated = this.updateService.getPutEntities(existing, updates);

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
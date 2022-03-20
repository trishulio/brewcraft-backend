package io.company.brewcraft.service;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import io.company.brewcraft.model.BaseIaasRole;
import io.company.brewcraft.model.IaasRole;
import io.company.brewcraft.model.Identified;
import io.company.brewcraft.model.UpdateIaasRole;

@Transactional
public class IaasRoleService extends BaseService implements CrudService<String, IaasRole, BaseIaasRole, UpdateIaasRole, IaasRoleAccessor> {
    private final IaasRoleIaasRepository iaasRepo;

    private UpdateService<String, IaasRole, BaseIaasRole, UpdateIaasRole> updateService;

    public IaasRoleService(UpdateService<String, IaasRole, BaseIaasRole, UpdateIaasRole> updateService, IaasRoleIaasRepository iaasRepo) {
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
    public long delete(Set<String> ids) {
        this.iaasRepo.delete(ids);

        return ids.size();
    }

    @Override
    public long delete(String id) {
        this.iaasRepo.delete(List.of(id));

        return 1;
    }

    @Override
    public IaasRole get(String id) {
        return this.iaasRepo.get(List.of(id)).get(0);
    }

    public List<IaasRole> getAll(Set<String> ids) {
        return this.iaasRepo.get(ids);
    }

    @Override
    public List<IaasRole> getByIds(Collection<? extends Identified<String>> idProviders) {
        Set<String> ids = idProviders.stream()
                    .filter(Objects::nonNull)
                    .map(provider -> provider.getId())
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());

        return this.iaasRepo.get(ids);
    }

    @Override
    public List<IaasRole> getByAccessorIds(Collection<? extends IaasRoleAccessor> accessors) {
        List<IaasRole> idProviders = accessors.stream()
                                    .filter(Objects::nonNull)
                                    .map(accessor -> accessor.getIaasRole())
                                    .filter(Objects::nonNull)
                                    .toList();
        return getByIds(idProviders);
    }

    @Override
    public List<IaasRole> add(List<BaseIaasRole> additions) {
        if (additions == null) {
            return null;
        }

        List<IaasRole> roles = this.updateService.getAddEntities(additions);

        return iaasRepo.add(roles);
    }

    @Override
    public List<IaasRole> put(List<UpdateIaasRole> updates) {
        if (updates == null) {
            return null;
        }

        List<IaasRole> updated = this.updateService.getPutEntities(null, updates);

        return iaasRepo.put(updated);
    }

    @Override
    public List<IaasRole> patch(List<UpdateIaasRole> updates) {
        if (updates == null) {
            return null;
        }

        List<IaasRole> existing = this.getByIds(updates);

        List<IaasRole> updated = this.updateService.getPatchEntities(existing, updates);

        return iaasRepo.put(updated);
    }
}

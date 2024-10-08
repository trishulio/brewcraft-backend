package io.company.brewcraft.service;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;

import io.company.brewcraft.model.Identified;
import io.company.brewcraft.model.user.BaseUserRole;
import io.company.brewcraft.model.user.UpdateUserRole;
import io.company.brewcraft.model.user.UserRole;
import io.company.brewcraft.model.user.UserRoleAccessor;
import io.company.brewcraft.repository.WhereClauseBuilder;
import io.company.brewcraft.service.exception.EntityNotFoundException;

@Transactional
public class UserRoleService extends BaseService implements CrudService<Long, UserRole, BaseUserRole, UpdateUserRole, UserRoleAccessor> {
    private static final Logger log = LoggerFactory.getLogger(UserRoleService.class);

    private final UpdateService<Long, UserRole, BaseUserRole, UpdateUserRole> updateService;
    private final RepoService<Long, UserRole, UserRoleAccessor> repoService;

    public UserRoleService(UpdateService<Long, UserRole, BaseUserRole, UpdateUserRole> updateService, RepoService<Long, UserRole, UserRoleAccessor> repoService) {
        this.updateService = updateService;
        this.repoService = repoService;
    }

    public Page<UserRole> getUserRoles(
        Set<Long> ids,
        Set<Long> excludeIds,
        Set<String> names,
        SortedSet<String> sort,
        boolean orderAscending,
        int page,
        int size
    ) {
        final Specification<UserRole> spec= WhereClauseBuilder.builder()
                                                                     .in(UserRole.FIELD_ID, ids)
                                                                     .not().in(UserRole.FIELD_ID, excludeIds)
                                                                     .like(UserRole.FIELD_NAME, names)
                                                                     .build();

        return this.repoService.getAll(spec, sort, orderAscending, page, size);
    }

    @Override
    public UserRole get(Long id) {
        return this.repoService.get(id);
    }

    @Override
    public List<UserRole> getByIds(Collection<? extends Identified<Long>> idProviders) {
        return this.repoService.getByIds(idProviders);
    }

    @Override
    public List<UserRole> getByAccessorIds(Collection<? extends UserRoleAccessor> accessors) {
        return this.repoService.getByAccessorIds(accessors, accessor -> accessor.getRole());
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
    public long delete(Set<Long> ids) {
        return this.repoService.delete(ids);
    }

    @Override
    public long delete(Long id) {
        return this.repoService.delete(id);
    }

    @Override
    public List<UserRole> add(final List<BaseUserRole> additions) {
        if (additions == null) {
            return null;
        }

        final List<UserRole> entities = this.updateService.getAddEntities(additions);

        return this.repoService.saveAll(entities);
    }

    @Override
    public List<UserRole> put(List<UpdateUserRole> updates) {
        if (updates == null) {
            return null;
        }

        final List<UserRole> existing = this.repoService.getByIds(updates);
        final List<UserRole> updated = this.updateService.getPutEntities(existing, updates);

        return this.repoService.saveAll(updated);
    }

    @Override
    public List<UserRole> patch(List<UpdateUserRole> patches) {
        if (patches == null) {
            return null;
        }

        final List<UserRole> existing = this.repoService.getByIds(patches);
        if (existing.size() != patches.size()) {
            final Set<Long> existingIds = existing.stream().map(userRole -> userRole.getId()).collect(Collectors.toSet());
            final Set<Long> nonExistingIds = patches.stream().map(patch -> patch.getId()).filter(patchId -> !existingIds.contains(patchId)).collect(Collectors.toSet());

            throw new EntityNotFoundException(String.format("Cannot find userRoles with Ids: %s", nonExistingIds));
        }

        final List<UserRole> updated = this.updateService.getPatchEntities(existing, patches);

        return this.repoService.saveAll(updated);
    }
}

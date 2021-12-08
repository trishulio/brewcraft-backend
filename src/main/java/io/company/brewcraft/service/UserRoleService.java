package io.company.brewcraft.service;

import static io.company.brewcraft.repository.RepositoryUtil.*;

import java.util.Set;
import java.util.SortedSet;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import io.company.brewcraft.model.Measure;
import io.company.brewcraft.model.user.UserRole;
import io.company.brewcraft.repository.WhereClauseBuilder;
import io.company.brewcraft.repository.user.UserRoleRepository;

@Transactional
public class UserRoleService extends BaseService {

    private UserRoleRepository userRoleRepository;

    public UserRoleService(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    public Page<UserRole> getRoles(Set<Long> ids, SortedSet<String> sort, boolean orderAscending, int page, int size) {
        Specification<UserRole> spec = WhereClauseBuilder
                .builder()
                .in(Measure.FIELD_ID, ids)
                .build();
        Page<UserRole> userRoles = userRoleRepository.findAll(spec, pageRequest(sort, orderAscending, page, size));

        return userRoles;
    }
}

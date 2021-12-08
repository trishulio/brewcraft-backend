package io.company.brewcraft.service;

import static io.company.brewcraft.repository.RepositoryUtil.*;

import java.util.Set;
import java.util.SortedSet;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import io.company.brewcraft.model.Measure;
import io.company.brewcraft.model.user.UserSalutation;
import io.company.brewcraft.repository.WhereClauseBuilder;
import io.company.brewcraft.repository.user.UserSalutationRepository;

@Transactional
public class UserSalutationService extends BaseService {

    private UserSalutationRepository userSalutationRepository;

    public UserSalutationService(UserSalutationRepository userSalutationRepository) {
        this.userSalutationRepository = userSalutationRepository;
    }

    public Page<UserSalutation> getSalutations(Set<Long> ids, SortedSet<String> sort, boolean orderAscending, int page, int size) {
        Specification<UserSalutation> spec = WhereClauseBuilder
                .builder()
                .in(Measure.FIELD_ID, ids)
                .build();
        Page<UserSalutation> userSalutations = userSalutationRepository.findAll(spec, pageRequest(sort, orderAscending, page, size));

        return userSalutations;
    }
}

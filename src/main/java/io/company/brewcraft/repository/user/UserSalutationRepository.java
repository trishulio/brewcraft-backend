package io.company.brewcraft.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import io.company.brewcraft.model.user.UserSalutation;

public interface UserSalutationRepository extends JpaRepository<UserSalutation, Long>, JpaSpecificationExecutor<UserSalutation>, EnhancedUserSalutationRepository {
}

package io.company.brewcraft.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import io.company.brewcraft.model.user.UserRoleType;

public interface UserRoleTypeRepository extends JpaRepository<UserRoleType, Long>, JpaSpecificationExecutor<UserRoleType>, EnhancedUserRoleTypeRepository {
}

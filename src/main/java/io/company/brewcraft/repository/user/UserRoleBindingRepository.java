package io.company.brewcraft.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import io.company.brewcraft.model.user.UserRoleBinding;

public interface UserRoleBindingRepository extends JpaRepository<UserRoleBinding, Long>, JpaSpecificationExecutor<UserRoleBinding>, EnhancedUserRoleBindingRepository {
}

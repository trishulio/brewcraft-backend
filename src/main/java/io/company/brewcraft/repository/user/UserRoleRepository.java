package io.company.brewcraft.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import io.company.brewcraft.model.user.UserRole;
import io.company.brewcraft.repository.ExtendedRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, Long>, JpaSpecificationExecutor<UserRole>, ExtendedRepository<Long> {
    @Override
    @Query("select count(i) > 0 from user_role ur where ur.id in (:ids)")
    boolean existsByIds(Iterable<Long> ids);

    @Override
    @Modifying
    @Query("delete from user_role ur where ur.id in (:ids)")
    int deleteByIds(Iterable<Long> ids);
}

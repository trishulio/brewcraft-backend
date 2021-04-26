package io.company.brewcraft.repository.user;

import io.company.brewcraft.model.user.UserRoleType;
import io.company.brewcraft.model.user.UserSalutation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRoleTypeRepository extends JpaRepository<UserRoleType, Long> {

    @Query("select r from user_role_type r where r.name = :name")
    Optional<UserRoleType> findByName(@Param("name") String name);
}

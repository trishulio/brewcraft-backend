package io.company.brewcraft.repository.user;

import io.company.brewcraft.model.user.UserSalutation;
import io.company.brewcraft.model.user.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserSalutationRepository extends JpaRepository<UserSalutation, Long> {

    @Query("select s from user_salutation s where s.name = :name")
    Optional<UserSalutation> findByName(@Param("name") String name);
}

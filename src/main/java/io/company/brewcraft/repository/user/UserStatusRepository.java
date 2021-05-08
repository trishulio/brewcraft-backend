package io.company.brewcraft.repository.user;

import io.company.brewcraft.model.user.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserStatusRepository extends JpaRepository<UserStatus, Long> {

    @Query("select s from user_status s where s.name = :name")
    Optional<UserStatus> findByName(@Param("name") String name);
}

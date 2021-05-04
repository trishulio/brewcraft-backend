package io.company.brewcraft.repository.user;

import io.company.brewcraft.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User>, EnhancedUserRepository {

    @Query("select u.userName from User u where u.id = :id")
    Optional<String> findUserNamePerId(@Param("id") Long id);

    @Query("select u.email from User u where u.id = :id")
    Optional<String> findEmailPerId(@Param("id") Long id);
}

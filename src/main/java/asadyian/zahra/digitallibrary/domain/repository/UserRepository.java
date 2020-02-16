package asadyian.zahra.digitallibrary.domain.repository;

import asadyian.zahra.digitallibrary.domain.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
    UserEntity findByEmailOrUsername(String email, String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    List<UserEntity> findAllByIdIn(Set<String> ids);
/*
    @Modifying
    @Query("UPDATE UserEntity u set u.failedLoginAttempts = u.failedLoginAttempts + 1 WHERE u.id = :id")
    Integer updateFailedLoginAttempts(@Param("id") String id);*/
}

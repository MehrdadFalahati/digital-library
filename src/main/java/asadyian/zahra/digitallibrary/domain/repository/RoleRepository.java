package asadyian.zahra.digitallibrary.domain.repository;

import asadyian.zahra.digitallibrary.domain.entities.RoleEntity;
import asadyian.zahra.digitallibrary.domain.entities.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    RoleEntity findByName(RoleName roleName);
}

package asadyian.zahra.digitallibrary.domain.repository;

import asadyian.zahra.digitallibrary.domain.entities.AttachmentTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttachmentTypeRepository extends JpaRepository<AttachmentTypeEntity, Long> {
}

package asadyian.zahra.digitallibrary.domain.repository;

import asadyian.zahra.digitallibrary.domain.entities.AttachmentTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttachmentTypeRepository extends JpaRepository<AttachmentTypeEntity, Long> {
    List<AttachmentTypeEntity> findAllByTitle(String title);
}

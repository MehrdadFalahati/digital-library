package asadyian.zahra.digitallibrary.domain.repository;

import asadyian.zahra.digitallibrary.domain.entities.ContentAttachmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContentAttachmentRepository extends JpaRepository<ContentAttachmentEntity, Long> {
    List<ContentAttachmentEntity> findAllByContentId(Long contentId);
}

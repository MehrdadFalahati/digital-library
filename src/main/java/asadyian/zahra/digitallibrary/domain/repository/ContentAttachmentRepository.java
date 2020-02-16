package asadyian.zahra.digitallibrary.domain.repository;

import asadyian.zahra.digitallibrary.domain.entities.ContentAttachmentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContentAttachmentRepository extends JpaRepository<ContentAttachmentEntity, Long> {
    Page<ContentAttachmentEntity> findAllByContentId(Long contentId, Pageable pageable);
}

package asadyian.zahra.digitallibrary.domain.repository;

import asadyian.zahra.digitallibrary.domain.entities.ContentTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ContentTypeRepository extends JpaRepository<ContentTypeEntity, Long> {
    List<ContentTypeEntity> findByTitleContaining(String title);
}

package asadyian.zahra.digitallibrary.domain.repository;

import asadyian.zahra.digitallibrary.domain.entities.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, String> {
}

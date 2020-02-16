package asadyian.zahra.digitallibrary.domain.repository;

import asadyian.zahra.digitallibrary.domain.entities.feedback.EmotionEntity;
import asadyian.zahra.digitallibrary.domain.entities.feedback.EmotionType;
import asadyian.zahra.digitallibrary.domain.repository.dao.EmotionDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmotionRepository extends JpaRepository<EmotionEntity, String>, EmotionDao {
    EmotionEntity findByContentIdAndUserId(Long contentId, String userId);
    long countByContentIdAndEmotionType(Long contentId, EmotionType type);
}

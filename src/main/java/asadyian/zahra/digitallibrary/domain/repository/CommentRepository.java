package asadyian.zahra.digitallibrary.domain.repository;

import asadyian.zahra.digitallibrary.domain.entities.feedback.CommentEntity;
import asadyian.zahra.digitallibrary.domain.repository.dao.CommentDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, String>, CommentDao {
    long countByContentIdAndReplyIsNull(Long contentId);
    long countByReplyId(String replyId);
    void deleteByReplyId(String replyId);
    Page<CommentEntity> findAllByReplyId(String replyId, Pageable pageable);
}

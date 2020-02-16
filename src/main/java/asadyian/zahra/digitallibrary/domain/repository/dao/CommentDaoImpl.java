package asadyian.zahra.digitallibrary.domain.repository.dao;

import asadyian.zahra.digitallibrary.domain.entities.feedback.CommentEntity;
import asadyian.zahra.digitallibrary.domain.model.CommentReplyCountResult;
import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentDaoImpl implements CommentDao {
    private final EntityManager entityManager;

    @Override
    public List<CommentReplyCountResult> groupByCommentIds(List<String> commentIds) {
        SessionFactory sessionFactory = entityManager.unwrap(SessionFactory.class);
        DetachedCriteria criteria = DetachedCriteria.forClass(CommentEntity.class);

        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.in(CommentEntity.PROP_REPLY + "." + CommentEntity.PROP_ID, commentIds));

        criteria.add(conjunction);
        criteria.setProjection(Projections.projectionList()
                .add(Projections.alias(Projections.rowCount(), CommentReplyCountResult.PROP_COUNT))
                .add(Projections.alias(Projections.groupProperty(CommentEntity.PROP_REPLY + "." + CommentEntity.PROP_ID), CommentReplyCountResult.PROP_ID)));


        criteria.setResultTransformer(new AliasToBeanResultTransformer(CommentReplyCountResult.class));

        return criteria.getExecutableCriteria(sessionFactory.getCurrentSession()).list();
    }
}

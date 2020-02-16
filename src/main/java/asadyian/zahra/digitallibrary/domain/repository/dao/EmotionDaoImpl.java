package asadyian.zahra.digitallibrary.domain.repository.dao;

import asadyian.zahra.digitallibrary.domain.entities.feedback.EmotionEntity;
import asadyian.zahra.digitallibrary.domain.model.EmotionCountResult;
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
public class EmotionDaoImpl implements EmotionDao {
    private final EntityManager entityManager;

    @Override
    public List<EmotionCountResult> groupByEmotionType(Long contentId) {
        SessionFactory sessionFactory = entityManager.unwrap(SessionFactory.class);
        DetachedCriteria criteria = DetachedCriteria.forClass(EmotionEntity.class);

        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq(EmotionEntity.PROP_CONTENT + "." + EmotionEntity.PROP_ID, contentId));

        criteria.add(conjunction);
        criteria.setProjection(Projections.projectionList()
                .add(Projections.alias(Projections.rowCount(), EmotionCountResult.PROP_COUNT))
                .add(Projections.alias(Projections.groupProperty(EmotionEntity.PROP_EMOTION_TYPE), EmotionCountResult.PROP_TYPE)));


        criteria.setResultTransformer(new AliasToBeanResultTransformer(EmotionCountResult.class));

        return criteria.getExecutableCriteria(sessionFactory.getCurrentSession()).list();
    }
}

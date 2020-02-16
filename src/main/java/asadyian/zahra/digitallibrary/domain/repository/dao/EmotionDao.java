package asadyian.zahra.digitallibrary.domain.repository.dao;

import asadyian.zahra.digitallibrary.domain.model.EmotionCountResult;

import java.util.List;

public interface EmotionDao {
    List<EmotionCountResult> groupByEmotionType(Long contentId);
}

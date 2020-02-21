package asadyian.zahra.digitallibrary.service.feedback;

import asadyian.zahra.digitallibrary.controller.model.feedback.SaveEmotionRequest;
import asadyian.zahra.digitallibrary.domain.entities.feedback.EmotionType;
import asadyian.zahra.digitallibrary.domain.model.EmotionResult;

public interface EmotionHandler {

	void addEmotion(SaveEmotionRequest emotion, Long contentId, String userId);
	
	void removeEmotion(Long contentId, String userId);
	
	long countByType(Long contentId, EmotionType type);

	EmotionResult count(Long contentId, String userId);

	Boolean isLike(Long contentId, String id);
}

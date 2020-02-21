package asadyian.zahra.digitallibrary.service.feedback.impl;

import asadyian.zahra.digitallibrary.controller.model.feedback.SaveEmotionRequest;
import asadyian.zahra.digitallibrary.domain.entities.ContentEntity;
import asadyian.zahra.digitallibrary.domain.entities.UserEntity;
import asadyian.zahra.digitallibrary.domain.entities.feedback.EmotionEntity;
import asadyian.zahra.digitallibrary.domain.entities.feedback.EmotionType;
import asadyian.zahra.digitallibrary.domain.model.EmotionCountResult;
import asadyian.zahra.digitallibrary.domain.model.EmotionResult;
import asadyian.zahra.digitallibrary.domain.repository.EmotionRepository;
import asadyian.zahra.digitallibrary.service.ContentService;
import asadyian.zahra.digitallibrary.service.UserService;
import asadyian.zahra.digitallibrary.service.feedback.EmotionHandler;
import asadyian.zahra.digitallibrary.service.feedback.LocalizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RdbmsEmotionHandler implements EmotionHandler {
	
	private final EmotionRepository repository;
	private final ContentService contentService;
	private final UserService userService;


	@Override
	@Transactional
	public void addEmotion(SaveEmotionRequest emotion, Long contentId, String userId) {
		if (emotion == null){
			throw new LocalizedException("emotion_is_null");
		}
		if (emotion.getType() == null) {
			throw new LocalizedException("emotion_type_is_null");
		}
		ContentEntity content = checkExistCase(contentId);
		UserEntity user = userService.singleUser(userId);
		if (user == null){
			throw new LocalizedException("emotion_person_dose_not_exist");
		}
		EmotionEntity entity = findByContentAndPerson(contentId, user.getId());
		if (Objects.nonNull(entity) && entity.getEmotionType() != emotion.getType()){
			entity.setEmotionType(emotion.getType());
			repository.save(entity);
			return;
		}
		entity = createEntity(emotion, content, user);
		repository.save(entity);
	}

	EmotionEntity findByContentAndPerson(Long contentId, String userId) {
		return repository.findByContentIdAndUserId(contentId, userId);
	}

	EmotionEntity createEntity(SaveEmotionRequest emotion, ContentEntity content,
							   UserEntity user) {
		EmotionEntity entity = new EmotionEntity();
		entity.setEmotionType(emotion.getType());
		entity.setContent(content);
		entity.setUser(user);
		return entity;
	}

	@Override
	@Transactional
	public void removeEmotion(Long contentId, String userId) {
        if (contentId == null) {
            throw new LocalizedException("emotion_caseId_is_null");
        }
		UserEntity user = userService.singleUser(userId);
		if (user == null){
			throw new LocalizedException("emotion_person_dose_not_exist");
		}
		EmotionEntity entity = findByContentAndPerson(contentId, user.getId());
		if(entity == null) {
			throw new LocalizedException("emotion_does_not_exist");
		}
		if (!userId.equals(entity.getUser().getId())) {
			throw new LocalizedException("emotion_can_not_remove");
		}
		repository.delete(entity);
	}

	@Override
	@Transactional(readOnly = true)
	public long countByType(Long contentId, EmotionType type) {
		checkExistCase(contentId);
		if (type == null){
			throw new LocalizedException("emotion_type_is_null");
		}
		return repository.countByContentIdAndEmotionType(contentId, type);
	}

	@Override
	@Transactional(readOnly = true)
	public EmotionResult count(Long contentId, String userId) {
		checkExistCase(contentId);
		UserEntity user = userService.singleUser(userId);
		EmotionEntity entity = findByContentAndPerson(contentId, user.getId());
		EmotionResult result = new EmotionResult();
		if (entity != null) {
			result.setCurrentType(entity.getEmotionType());
		}
		List<EmotionCountResult> countResults = repository.groupByEmotionType(contentId);
		countResults.forEach(count -> result.getItem().put(count.getType(), count.getCount()));
		return result;
	}

	@Override
	@Transactional(readOnly = true)
	public Boolean isLike(Long contentId, String userId) {
		EmotionEntity emotion = repository.findByContentIdAndUserId(contentId, userId);
		if (emotion == null) {
			return false;
		}
		return emotion.getEmotionType() == EmotionType.LIKE;
	}


	private ContentEntity checkExistCase(Long contentId) {
		if (contentId == null) {
			throw new LocalizedException("emotion_caseId_is_null");
		}
		ContentEntity contentEntity = contentService.findById(contentId);
		if (contentEntity == null) {
			throw new LocalizedException("emotion_case_does_not_exist");
		}
		return contentEntity;
	}

}

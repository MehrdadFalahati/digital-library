package asadyian.zahra.digitallibrary.service.feedback.impl;

import asadyian.zahra.digitallibrary.controller.model.feedback.SaveCommentRequest;
import asadyian.zahra.digitallibrary.domain.entities.ContentEntity;
import asadyian.zahra.digitallibrary.domain.entities.UserEntity;
import asadyian.zahra.digitallibrary.domain.entities.feedback.CommentEntity;
import asadyian.zahra.digitallibrary.domain.model.CommentDto;
import asadyian.zahra.digitallibrary.domain.model.CommentReplyCountResult;
import asadyian.zahra.digitallibrary.domain.repository.CommentRepository;
import asadyian.zahra.digitallibrary.service.ContentService;
import asadyian.zahra.digitallibrary.service.UserService;
import asadyian.zahra.digitallibrary.service.feedback.CommentHandler;
import asadyian.zahra.digitallibrary.service.feedback.LocalizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RdbmsCommentHandler implements CommentHandler {

    private final CommentRepository repository;
    private final UserService userService;
    private final ContentService contentService;

    @Override
    @Transactional(readOnly = true)
    public Page<CommentDto> searchComment(Long contentId, Pageable pageable) {
        Page<CommentEntity> dataPage = repository.findAllByContentId(contentId, pageable);
        if (dataPage == null || dataPage.getContent().isEmpty()) {
            return new PageImpl<>(new ArrayList<>());
        }
        Map<String, UserEntity> users = fetchPersons(dataPage.getContent());
       // Map<String, CommentReplyCountResult> countReplies = countRepliesByCommentIds(dataPage.getContent());
        List<CommentDto> commentDtos = createCommentDtoList(dataPage.getContent(), users, new HashMap<>());
        return new PageImpl<>(commentDtos, dataPage.getPageable(), dataPage.getTotalElements());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CommentDto> searchCommentReplies(String commentId, Pageable pageable) {
        Page<CommentEntity> dataPage = repository.findAllByReplyId(commentId, pageable);
        if (dataPage == null || dataPage.getContent().isEmpty()) {
            return new PageImpl<>(new ArrayList<>());
        }
        Map<String, UserEntity> users = fetchPersons(dataPage.getContent());
        Map<String, CommentReplyCountResult> countReplies = countRepliesByCommentIds(dataPage.getContent());
        List<CommentDto> commentDtos = createCommentDtoList(dataPage.getContent(), users, countReplies);
        return new PageImpl<>(commentDtos, dataPage.getPageable(), dataPage.getTotalElements());
    }

    Map<String, UserEntity> fetchPersons(List<CommentEntity> items) {
        Set<String> userIds = items.stream().map(comment -> comment.getUser().getId()).collect(Collectors.toSet());
        List<UserEntity> userItems = userService.loadByIds(userIds);
        return userItems.stream()
                .collect(Collectors.toMap(UserEntity::getId, user -> user));
    }

    Map<String, CommentReplyCountResult> countRepliesByCommentIds(List<CommentEntity> items) {
        List<String> commentIds = items.stream().map(CommentEntity::getId).collect(Collectors.toList());
        List<CommentReplyCountResult> repliesCounts = repository.groupByCommentIds(commentIds);
        return repliesCounts.stream()
                .collect(Collectors.toMap(CommentReplyCountResult::getId, repliesCount -> repliesCount));
    }

    List<CommentDto> createCommentDtoList(List<CommentEntity> items, Map<String, UserEntity> users,
                                          Map<String, CommentReplyCountResult> countReplies) {
        return items.stream()
                .map(comment -> {
                    UserEntity userEntity = users.get(comment.getUser().getId());
                    CommentReplyCountResult commentReplyCountResult = countReplies.getOrDefault(comment.getId(), new CommentReplyCountResult());
                    CommentDto commentDto = createCommentDto(comment, userEntity);
                    commentDto.setReplyCount(commentReplyCountResult.getCount());
                    return commentDto;
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CommentDto load(String id) {
        CommentEntity entity = loadInternal(id);
        return createCommentDto(entity, entity.getUser());
    }

    CommentDto createCommentDto(CommentEntity entity, UserEntity user) {
        return CommentDto.builder()
                .id(entity.getId())
                .personId(user == null ? null : user.getId())
                .personToStr(user == null ? null : user.getUsername())
                .contentId(entity.getContent() == null ? null : entity.getContent().getId())
                .replyId(entity.getReply() == null ? null : entity.getReply().getId())
                .comment(entity.getBody())
                .creationTime(entity.getCreatedAt())
                .build();
    }

    @Override
    @Transactional
    public CommentDto addComment(SaveCommentRequest comment, Long contentId, String userId) {
        if (comment == null) {
            throw new LocalizedException("comment_is_null");
        }
        if (comment.getComment() == null) {
            throw new LocalizedException("comment_body_is_null");
        }
        if (contentId == null) {
            throw new LocalizedException("comment_case_id_is_null");
        }
        ContentEntity content = contentService.findById(contentId);
        if (content == null) {
            throw new LocalizedException("comment_case_dose_not_exist");
        }
        UserEntity user = userService.singleUser(userId);
        if (user == null) {
            throw new LocalizedException("comment_person_dose_not_exist");
        }
        CommentEntity entity = createCommentEntity(comment, content, user);
        repository.save(entity);
        return createCommentDto(entity, entity.getUser());
    }

    CommentEntity createCommentEntity(SaveCommentRequest comment, ContentEntity content, UserEntity user) {
        CommentEntity entity = new CommentEntity();
        entity.setBody(comment.getComment());
        entity.setContent(content);
        entity.setUser(user);
        if (comment.getReplyId() != null) {
            CommentEntity reply = loadInternal(comment.getReplyId());
            entity.setReply(reply);
        }
        return entity;
    }

    @Override
    @Transactional
    public void removeComment(String id, String userId) {
        CommentEntity comment = loadInternal(id);
        if (!userId.equals(comment.getUser().getId())) {
            throw new LocalizedException("comment_can_not_remove");
        }
        remove(comment);
    }

    private CommentEntity loadInternal(String id) {
        CommentEntity entity = repository.findById(id).orElse(null);
        if (entity == null) {
            throw new LocalizedException("comment_does_not_exist");
        }
        return entity;
    }

    @Override
    @Transactional(readOnly = true)
    public long countTopComments(Long contentId) {
        if (contentId == null) {
            throw new LocalizedException("comment_case_id_is_null");
        }
        return repository.countByContentIdAndReplyIsNull(contentId);
    }

    @Override
    @Transactional(readOnly = true)
    public long countReplies(String replyId) {
        if (replyId == null) {
            throw new LocalizedException("comment_parent_id_is_null");
        }
        return repository.countByReplyId(replyId);
    }

    private void remove(CommentEntity comment) {
        repository.deleteByReplyId(comment.getReply().getId());
        repository.delete(comment);
    }
}

package asadyian.zahra.digitallibrary.controller;

import asadyian.zahra.digitallibrary.controller.model.feedback.SaveCommentRequest;
import asadyian.zahra.digitallibrary.controller.model.feedback.SaveEmotionRequest;
import asadyian.zahra.digitallibrary.domain.entities.feedback.EmotionType;
import asadyian.zahra.digitallibrary.domain.model.CommentDto;
import asadyian.zahra.digitallibrary.domain.model.EmotionResult;
import asadyian.zahra.digitallibrary.security.CurrentUser;
import asadyian.zahra.digitallibrary.security.UserPrincipal;
import asadyian.zahra.digitallibrary.service.feedback.CommentHandler;
import asadyian.zahra.digitallibrary.service.feedback.EmotionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/feedback/content/{contentId}")
@RequiredArgsConstructor
@PreAuthorize("hasRole('USER')")
public class FeedbackController {

    private final CommentHandler commentHandler;
    private final EmotionHandler emotionHandler;


    @PostMapping("/comment/add")
    public CommentDto addComment(@RequestBody SaveCommentRequest entity, @PathVariable("contentId") Long contentId,
                          @CurrentUser UserPrincipal userPrincipal) {
        return commentHandler.addComment(entity, contentId, userPrincipal.getId());
    }

    @GetMapping("/comment/{id}/load")
    public CommentDto load(@PathVariable("id") String commentId) {
        return commentHandler.load(commentId);
    }

    @PostMapping("/comment/{id}/remove")
    ResponseEntity removeComment(@PathVariable("id") String commentId, @CurrentUser UserPrincipal userPrincipal) {
        commentHandler.removeComment(commentId, userPrincipal.getId());
        return ResponseEntity.ok("remove successful");
    }

    @GetMapping("/comment/fetch")
    public Page<CommentDto> fetchComments(@PathVariable("contentId") Long contentId, Pageable pageable) {
        return commentHandler.searchComment(contentId, pageable);
    }

    @GetMapping("/comment/{id}/replies")
    public Page<CommentDto> fetchReplies(@PathVariable("id") String commentId, Pageable pageable) {
        return commentHandler.searchCommentReplies(commentId, pageable);
    }

    @GetMapping("/comment/count")
    public Long countComment(@PathVariable("contentId") Long contentId) {
        return commentHandler.countTopComments(contentId);
    }

    @GetMapping("/comment/{id}/replies-count")
    public Long countReplies(@PathVariable("id") String commentId) {
        return commentHandler.countReplies(commentId);
    }

    @PostMapping("/emotion/add")
    public Boolean addEmotion(@RequestBody SaveEmotionRequest entity, @PathVariable("contentId") Long contentId,
                              @CurrentUser UserPrincipal userPrincipal) {
        emotionHandler.addEmotion(entity, contentId, userPrincipal.getId());
        return entity.getType() == EmotionType.LIKE;
    }

    @PostMapping("/emotion/remove")
    public ResponseEntity removeEmotion(@PathVariable("contentId") Long contentId, @CurrentUser UserPrincipal userPrincipal) {
        emotionHandler.removeEmotion(contentId, userPrincipal.getId());
        return ResponseEntity.ok("remove emotion successful");
    }

    @GetMapping("/emotion/{type}/count")
    public Long countEmotionByType(@PathVariable("contentId") Long contentId, @PathVariable("type") EmotionType type) {
       return emotionHandler.countByType(contentId, type);
    }

    @GetMapping("/emotion/count")
    public EmotionResult countAllEmotions(@PathVariable("contentId") Long contentId, @CurrentUser UserPrincipal userPrincipal) {
        return emotionHandler.count(contentId, userPrincipal.getId());
    }

    @GetMapping("/emotion")
    public Boolean isLike(@PathVariable("contentId") Long contentId,
                          @CurrentUser UserPrincipal userPrincipal) {
        return emotionHandler.isLike(contentId, userPrincipal.getId());
    }
}

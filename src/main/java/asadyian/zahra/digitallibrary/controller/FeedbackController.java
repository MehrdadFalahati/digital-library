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
    CommentDto addComment(@RequestBody SaveCommentRequest entity, @PathVariable("contentId") Long contentId,
                          @CurrentUser UserPrincipal userPrincipal) {
        return commentHandler.addComment(entity, contentId, userPrincipal.getId());
    }

    @GetMapping("/comment/{id}/load")
    CommentDto load(@RequestParam("id") String commentId) {
        return commentHandler.load(commentId);
    }

    @PostMapping("/comment/{id}/remove")
    ResponseEntity removeComment(@RequestParam("id") String commentId, @CurrentUser UserPrincipal userPrincipal) {
        commentHandler.removeComment(commentId, userPrincipal.getId());
        return ResponseEntity.ok("remove successful");
    }

    @GetMapping("/comment/fetch")
    Page<CommentDto> fetchComments(Pageable pageable) {
        return commentHandler.searchComment(pageable);
    }

    @GetMapping("/comment/{id}/replies")
    Page<CommentDto> fetchReplies(@RequestParam("id") String commentId, Pageable pageable) {
        return commentHandler.searchCommentReplies(commentId, pageable);
    }

    @GetMapping("/comment/count")
    Long countComment(@PathVariable("contentId") Long contentId) {
        return commentHandler.countTopComments(contentId);
    }

    @GetMapping("/comment/{id}/replies-count")
    Long countReplies(@RequestParam("id") String commentId) {
        return commentHandler.countReplies(commentId);
    }

    @PostMapping("/emotion/add")
    ResponseEntity addEmotion(@RequestBody SaveEmotionRequest entity, @PathVariable("contentId") Long contentId,
                              @CurrentUser UserPrincipal userPrincipal) {
        emotionHandler.addEmotion(entity, contentId, userPrincipal.getId());
        return ResponseEntity.ok("add emotion successful");
    }

    @PostMapping("/emotion/remove")
    ResponseEntity removeEmotion(@PathVariable("contentId") Long contentId, @CurrentUser UserPrincipal userPrincipal) {
        emotionHandler.removeEmotion(contentId, userPrincipal.getId());
        return ResponseEntity.ok("remove emotion successful");
    }

    @GetMapping("/emotion/{type}/count")
    Long countEmotionByType(@PathVariable("contentId") Long contentId, @RequestParam("type") EmotionType type) {
       return emotionHandler.countByType(contentId, type);
    }

    @GetMapping("/emotion/count")
    EmotionResult countAllEmotions(@PathVariable("contentId") Long contentId, @CurrentUser UserPrincipal userPrincipal) {
        return emotionHandler.count(contentId, userPrincipal.getId());
    }


}

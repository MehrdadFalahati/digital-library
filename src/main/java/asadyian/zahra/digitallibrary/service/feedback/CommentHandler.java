package asadyian.zahra.digitallibrary.service.feedback;


import asadyian.zahra.digitallibrary.controller.model.feedback.SaveCommentRequest;
import asadyian.zahra.digitallibrary.domain.model.CommentDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentHandler {

	Page<CommentDto> searchComment(Pageable pageable);

	Page<CommentDto> searchCommentReplies(String commentId, Pageable pageable);
	
	CommentDto load(String id);
	
	CommentDto addComment(SaveCommentRequest comment, Long contentId, String userId);
	
	void removeComment(String id, String userId);
	
	long countTopComments(Long contentId);

	long countReplies(String parentId);
}

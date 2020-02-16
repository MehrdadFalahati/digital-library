package asadyian.zahra.digitallibrary.domain.repository.dao;

import asadyian.zahra.digitallibrary.domain.model.CommentReplyCountResult;

import java.util.List;

public interface CommentDao {
    List<CommentReplyCountResult> groupByCommentIds(List<String> commentIds);
}

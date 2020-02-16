package asadyian.zahra.digitallibrary.controller.model.feedback;

public class SaveCommentRequest {

    private String replyId;
    private String comment;

    public String getReplyId() {
        return replyId;
    }

    public void setReplyId(String replyId) {
        this.replyId = replyId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}

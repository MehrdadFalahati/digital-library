package asadyian.zahra.digitallibrary.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
	private String id;
	private String personId;
	private String personToStr;
	private Long contentId;
	private String replyId;
	private String comment;
	private long replyCount;
	private Date creationTime;
}

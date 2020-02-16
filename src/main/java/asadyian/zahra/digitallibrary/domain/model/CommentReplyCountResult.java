package asadyian.zahra.digitallibrary.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentReplyCountResult {

    public static final String PROP_ID = "id";
    public static final String PROP_COUNT = "count";

    private String id;
    private long count;

}

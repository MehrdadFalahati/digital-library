package asadyian.zahra.digitallibrary.controller.model.contentAttachment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContentAttachmentResponse {
    private Long id;
    private String name;


}

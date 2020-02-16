package asadyian.zahra.digitallibrary.controller.model.contentAttachment;

import asadyian.zahra.digitallibrary.domain.entities.ContentAttachmentEntity;
import asadyian.zahra.digitallibrary.domain.entities.ContentEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContentAttachmentRequest {
    private Long id;
    private String name;
    private ContentEntity content;

    public ContentAttachmentEntity convert2attachment() {
        return ContentAttachmentEntity.builder()
                .id(id)
                .name(name)
                .content(content)
                .build();
    }
}

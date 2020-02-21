package asadyian.zahra.digitallibrary.controller.model.contentAttachment;

import asadyian.zahra.digitallibrary.domain.entities.AttachmentTypeEntity;
import asadyian.zahra.digitallibrary.domain.entities.ContentAttachmentEntity;
import asadyian.zahra.digitallibrary.domain.entities.ContentEntity;
import asadyian.zahra.digitallibrary.domain.entities.FileEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContentAttachmentRequest {
    private Long id;
    private String name;
    private ContentEntity content;
    private FileEntity attachmentData;
    private AttachmentTypeEntity attachmentType;

    public ContentAttachmentEntity convert2attachment() {
        return ContentAttachmentEntity.builder()
                .id(id)
                .name(name)
                .content(content)
                .attachmentType(attachmentType)
                .attachmentData(attachmentData)
                .build();
    }
}

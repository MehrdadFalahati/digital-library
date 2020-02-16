package asadyian.zahra.digitallibrary.controller.model.attachmenttype;

import asadyian.zahra.digitallibrary.domain.entities.AttachmentTypeEntity;
import asadyian.zahra.digitallibrary.domain.entities.ContentTypeEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttachmentTypeRequest {
    private Long id;
    private String code;
    private String title;
    private ContentTypeEntity contentType;

    public AttachmentTypeEntity convert2AttachmentType() {
        return new AttachmentTypeEntity(id, code, title, contentType);
    }
}

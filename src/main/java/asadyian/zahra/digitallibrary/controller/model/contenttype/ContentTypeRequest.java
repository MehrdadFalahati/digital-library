package asadyian.zahra.digitallibrary.controller.model.contenttype;

import asadyian.zahra.digitallibrary.domain.entities.ContentTypeEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContentTypeRequest {
    private Long id;
    private String code;
    private String title;

    public ContentTypeEntity convert2ContentType() {
        return new ContentTypeEntity(id, code, title);
    }
}

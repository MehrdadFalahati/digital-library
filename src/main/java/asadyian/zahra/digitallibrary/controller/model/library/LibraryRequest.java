package asadyian.zahra.digitallibrary.controller.model.library;

import asadyian.zahra.digitallibrary.domain.entities.ContentTypeEntity;
import asadyian.zahra.digitallibrary.domain.entities.LibraryEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LibraryRequest {
    private Long id;
    private String code;
    private String title;
    private ContentTypeEntity contentType;

    public LibraryEntity convert2Library() {
        return new LibraryEntity(id, code, title, contentType);
    }
}

package asadyian.zahra.digitallibrary.controller.model.content;

import asadyian.zahra.digitallibrary.domain.entities.ContentEntity;
import asadyian.zahra.digitallibrary.domain.entities.ContentTypeEntity;
import asadyian.zahra.digitallibrary.domain.entities.FileEntity;
import asadyian.zahra.digitallibrary.domain.entities.LibraryEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ContentRequest {
    private Long id;
    private String name;
    private Date buyDate;
    private String description;
    private LibraryEntity library;
    private ContentTypeEntity contentType;
    private FileEntity contentData;

    public ContentEntity convert2content() {
        return ContentEntity.builder()
                .id(id)
                .name(name)
                .buyDate(buyDate)
                .description(description)
                .library(library)
                .contentType(contentType)
                .contentData(contentData)
                .build();
    }
}

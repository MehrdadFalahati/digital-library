package asadyian.zahra.digitallibrary.controller.model.content;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContentResponse {
    private Long id;
    private String name;
    private Date buyDate;
    private String description;
    private Library library;
    private String contentFileDataId;
    private ContentType contentType;

    public String getDownloadFileDataLink() {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/files/downloadFile/")
                .path(contentFileDataId)
                .toUriString();
    }

    public String getContentTypeToStr() {
        return contentType.title;
    }

    public String getLibraryToStr() {
        return library.title;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Library {
        private Long id;
        private String title;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ContentType {
        private Long id;
        private String title;
    }
}

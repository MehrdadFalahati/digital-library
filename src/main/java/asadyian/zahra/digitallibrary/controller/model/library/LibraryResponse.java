package asadyian.zahra.digitallibrary.controller.model.library;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LibraryResponse {
    private Long id;
    private String code;
    private String title;
    private ContentType contentType;

    public String getContentTypeToStr() {
        return contentType.title;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ContentType {
        private Long id;
        private String title;
    }
}

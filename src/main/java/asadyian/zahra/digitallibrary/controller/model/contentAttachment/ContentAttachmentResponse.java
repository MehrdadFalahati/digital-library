package asadyian.zahra.digitallibrary.controller.model.contentAttachment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContentAttachmentResponse {
    private Long id;
    private String name;
    private String fileId;
    private Content content;
    private AttachmentType attachmentType;

    public String getDownloadFileLink() {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/files/downloadFile/")
                .path(fileId)
                .toUriString();
    }

    public String getContentToStr() {
        return content.name;
    }

    public String getAttachmentTypeToStr() {
        return attachmentType.title;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Content {
        private Long id;
        private String name;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AttachmentType {
        private Long id;
        private String title;
    }

}

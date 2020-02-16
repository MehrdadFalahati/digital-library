package asadyian.zahra.digitallibrary.controller.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Getter
@Setter
public class MultipartFileModel {
    private String fileName;
    private String contentType;
    private byte[] bytes;

    public MultipartFileModel() {
    }

    public MultipartFileModel(MultipartFile file) throws IOException {
        setFileName(StringUtils.cleanPath(file.getOriginalFilename()));
        setContentType(file.getContentType());
        setBytes(file.getBytes());
    }
}


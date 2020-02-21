package asadyian.zahra.digitallibrary.controller.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class FileResponse {
    private String id;
    private String fileName;
}

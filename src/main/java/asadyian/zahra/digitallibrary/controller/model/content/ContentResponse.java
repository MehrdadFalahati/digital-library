package asadyian.zahra.digitallibrary.controller.model.content;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}

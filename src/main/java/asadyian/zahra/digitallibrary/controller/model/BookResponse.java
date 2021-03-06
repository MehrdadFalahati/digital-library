package asadyian.zahra.digitallibrary.controller.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookResponse {
    private Long id;
    private String name;
    private String description;
    private String imageUrl;
    private Long countLike;
    private Double rate;
}

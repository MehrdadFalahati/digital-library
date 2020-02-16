package asadyian.zahra.digitallibrary.controller.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class UserSummery {
    private String id;
    private String username;
    private String firstName;
    private String lastName;
}

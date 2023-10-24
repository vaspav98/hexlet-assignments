package exercise.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Setter
@Getter
public class AuthorDTO {
    private long id;
    private String firstName;
    private String lastName;
    private Date createdAt;
}

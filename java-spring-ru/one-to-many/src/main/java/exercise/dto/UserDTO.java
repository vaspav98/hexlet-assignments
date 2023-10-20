package exercise.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Setter
@Getter
public class UserDTO {
    private long id;

    private String name;

    private String email;

    private Date createdAt;
}

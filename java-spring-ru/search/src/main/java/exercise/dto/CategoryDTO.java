package exercise.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Setter
@Getter
public class CategoryDTO {
    private long id;
    private String name;
    private Date createdAt;
}

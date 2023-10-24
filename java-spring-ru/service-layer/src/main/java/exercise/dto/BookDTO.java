package exercise.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class BookDTO {
    private Long id;
    private Long authorId;
    private String authorFirstName;
    private String authorLastName;
    private String title;
    private Date createdAt;
    private Date updatedAt;
}

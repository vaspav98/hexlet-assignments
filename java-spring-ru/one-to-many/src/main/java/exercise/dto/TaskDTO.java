package exercise.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TaskDTO {
    private Long id;
    private Long assigneeId;
    private String title;
    private String description;
    private Date createdAt;
    private Date updatedAt;
}

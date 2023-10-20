package exercise.mapper;

import exercise.dto.TaskCreateDTO;
import exercise.dto.TaskDTO;
import exercise.dto.TaskUpdateDTO;
import exercise.exception.ResourceNotFoundException;
import exercise.model.Task;
import exercise.model.User;
import exercise.repository.UserRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class TaskMapper {

    // BEGIN
    @Autowired
    UserRepository userRepository;

    @Mapping(source = "assignee.id", target = "assigneeId")
    public abstract TaskDTO map(Task model);

    public Task map (TaskCreateDTO dto) {
        User assignee = userRepository.findById(dto.getAssigneeId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employee with id " + dto.getAssigneeId() + " was not found"));

        Task task = new Task();
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setAssignee(assignee);
        return task;
    }

    public void update(TaskUpdateDTO dto, Task model) {
        User assignee = userRepository.findById(dto.getAssigneeId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employee with id " + dto.getAssigneeId() + " was not found"));

        model.setTitle(dto.getTitle());
        model.setDescription(dto.getDescription());
        model.setAssignee(assignee);
    }
    // END

}

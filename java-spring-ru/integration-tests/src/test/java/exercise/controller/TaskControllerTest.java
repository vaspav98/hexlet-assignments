package exercise.controller;

import org.junit.jupiter.api.Test;
import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;
import org.instancio.Instancio;
import org.instancio.Select;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.datafaker.Faker;
import exercise.repository.TaskRepository;
import exercise.model.Task;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.transaction.annotation.Transactional;

// END
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
// BEGIN
public class TaskControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper om;

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    Faker faker;

    public Task createTask() {
        return Instancio.of(Task.class)
                .ignore(Select.field("id"))
                .supply(Select.field("title"), () -> faker.lorem().word())
                .supply(Select.field("description"), () -> faker.lorem().paragraph())
                .create();
    }

    @Test
    public void testShow() throws Exception {
        Task task = createTask();
        taskRepository.save(task);

        MvcResult result = mockMvc.perform(get("/tasks/" + task.getId()))
                .andExpect(status().isOk())
                .andReturn();

        String body = result.getResponse().getContentAsString();
        assertThatJson(body).and(
                a -> a.node("title").isEqualTo(task.getTitle()),
                a -> a.node("description").isEqualTo(task.getDescription())
        );
    }

    @Test
    public void testCreate() throws Exception {
        Task data = createTask();

        MockHttpServletRequestBuilder request = post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(data));

        mockMvc.perform(request)
                .andExpect(status().isCreated());

        Task addedTask = taskRepository.findByTitle(data.getTitle()).get();
        assertThat(addedTask.getTitle()).isEqualTo(data.getTitle());
        assertThat(addedTask.getDescription()).isEqualTo(data.getDescription());
    }

    @Test
    public void testPut() throws Exception {
        Task task = createTask();
        taskRepository.save(task);

        HashMap<String, String> data = new HashMap<>(new HashMap<>(Map.of("title", "hz")));

        MockHttpServletRequestBuilder request = put("/tasks/" + task.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(data));

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();

        Task updatedTask = taskRepository.findById(task.getId()).get();
        assertThat(updatedTask.getTitle()).isEqualTo("hz");
    }

    @Test
    public void testDelete() throws Exception {
        Task task = createTask();
        taskRepository.save(task);

        mockMvc.perform(delete("/tasks/" + task.getId()))
                .andExpect(status().isOk());

        Task destroyedTask = taskRepository.findById(task.getId()).orElse(null);
        assertThat(destroyedTask).isNull();
    }
    // END
}

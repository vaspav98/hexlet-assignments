package exercise;

import com.fasterxml.jackson.databind.ObjectMapper;
import exercise.model.Person;
import exercise.repository.PersonRepository;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import org.springframework.http.MediaType;

import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.junit.jupiter.Container;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.Map;

@SpringBootTest
@AutoConfigureMockMvc

// BEGIN
@Testcontainers
@Transactional
// END
public class AppTest {

    @Autowired
    private MockMvc mockMvc;

    // BEGIN
    @Autowired
    PersonRepository personRepository;

    // Аннотация отмечает контейнер, который будет автоматически запущен
    @Container
    // Создаём контейнер с СУБД PostgreSQL
    // В конструктор передаём имя образа, который будет скачан с Dockerhub
    // Если не указать версию, будет скачана последняя версия образа
    private static PostgreSQLContainer<?> database = new PostgreSQLContainer<>("postgres")
            // Создаём базу данных с указанным именем
            .withDatabaseName("dbname")
            // Указываем имя пользователя и пароль
            .withUsername("vaspav")
            .withPassword("123")
            // Скрипт, который будет выполнен при запуске контейнера и наполнит базу тестовыми данными
            .withInitScript("init.sql");

    // Так как мы не можем знать заранее, какой URL будет у базы данных в контейнере
    // Нам потребуется установить это свойство динамически
    @DynamicPropertySource
    public static void properties(DynamicPropertyRegistry registry) {
        // Устанавливаем URL базы данных
        registry.add("spring.datasource.url", database::getJdbcUrl);
        // Имя пользователя и пароль для подключения
        registry.add("spring.datasource.username", database::getUsername);
        registry.add("spring.datasource.password", database::getPassword);
        // Эти значения приложение будет использовать при подключении к базе данных
    }

    // END

    @Test
    void testCreatePerson() throws Exception {
        MockHttpServletResponse responsePost = mockMvc
                .perform(
                        post("/people")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"firstName\": \"Jackson\", \"lastName\": \"Bind\"}")
                )
                .andReturn()
                .getResponse();

        assertThat(responsePost.getStatus()).isEqualTo(200);

        MockHttpServletResponse response = mockMvc
                .perform(get("/people"))
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON.toString());
        assertThat(response.getContentAsString()).contains("Jackson", "Bind");
    }

    @Test
    void testIndex() throws Exception {
        MvcResult result = mockMvc.perform(get("/people"))
                .andReturn();

        String body = result.getResponse().getContentAsString();
        assertThat(result.getResponse().getStatus()).isEqualTo(200);
        assertThat(body).contains("John", "Doe", "Jassica", "Lock");
    }

    @Test
    void testShow() throws Exception {
        MvcResult result = mockMvc.perform(get("/people/1"))
                .andReturn();

        String body = result.getResponse().getContentAsString();
        System.out.println(body);
        assertThat(result.getResponse().getStatus()).isEqualTo(200);
        assertThat(body).contains("John", "Smith");
    }

    @Test
    void testUpdate() throws Exception {
        Map<String, String> data = Map.of("firstName", "Pavel", "lastName", "Vasilev");
        ObjectMapper om = new ObjectMapper();

        mockMvc.perform(patch("/people/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(data))
        );

        Person updatedPerson = personRepository.findById(1);
        assertThat(updatedPerson).isNotNull();
        assertThat(updatedPerson.getFirstName()).isEqualTo("Pavel");
        assertThat(updatedPerson.getLastName()).isEqualTo("Vasilev");
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete("/people/1"));

        Person destroyedPerson = personRepository.findById(1);
        assertThat(destroyedPerson).isNull();
    }

}





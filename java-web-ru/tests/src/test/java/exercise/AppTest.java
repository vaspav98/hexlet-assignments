package exercise;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import io.javalin.Javalin;
import io.ebean.DB;

import exercise.domain.User;
import exercise.domain.query.QUser;
import io.ebean.Database;

class AppTest {

    private static Javalin app;
    private static String baseUrl;

    // BEGIN
    @BeforeAll
    public static void beforeAll() {
        app = App.getApp();
        app.start();
        int port = app.port();
        baseUrl = "http://localhost:" + port;
    }

    @AfterAll
    public static void afterAll() {
        app.stop();
    }
    // END

    // Между тестами база данных очищается
    // Благодаря этому тесты не влияют друг на друга
    @BeforeEach
    void beforeEach() {
        Database db = DB.getDefault();
        db.truncate("users");
        User existingUser = new User("Wendell", "Legros", "a@a.com", "123456");
        existingUser.save();
    }

    @Test
    void testUsers() {

        // Выполняем GET запрос на адрес http://localhost:port/users
        HttpResponse<String> response = Unirest
            .get(baseUrl + "/users")
            .asString();
        // Получаем тело ответа
        String content = response.getBody();

        // Проверяем код ответа
        assertThat(response.getStatus()).isEqualTo(200);
        // Проверяем, что страница содержит определенный текст
        assertThat(response.getBody()).contains("Wendell Legros");
    }

    @Test
    void testNewUser() {

        HttpResponse<String> response = Unirest
            .get(baseUrl + "/users/new")
            .asString();

        assertThat(response.getStatus()).isEqualTo(200);
    }

    // BEGIN
    @Test
    void testCreateUserPositive() {
        HttpResponse<String> response = Unirest
                .post(baseUrl + "/users")
                .field("firstName", "Pavel")
                .field("lastName", "Vasilev")
                .field("email", "pv-paul@mail.ru")
                .field("password", "vaspav")
                .asString();

        assertThat(response.getStatus()).isEqualTo(302);

        User newUser = new QUser()
                .email.equalTo("pv-paul@mail.ru")
                .findOne();
        assertThat(newUser).isNotNull();
        assertThat(newUser.getFirstName()).isEqualTo("Pavel");
        assertThat(newUser.getLastName()).isEqualTo("Vasilev");
        assertThat(newUser.getEmail()).isEqualTo("pv-paul@mail.ru");
        assertThat(newUser.getPassword()).isEqualTo("vaspav");
    }

    @Test
    void testCreateUserNegative() {
        HttpResponse<String> response1 = Unirest
                .post(baseUrl + "/users")
                .field("firstName", "")
                .field("lastName", "Vasilev")
                .field("email", "pv-paul@mail.ru")
                .field("password", "vaspav")
                .asString();
        User invalidUser1 = new QUser()
                .email.equalTo("pv-paul@mail.ru")
                .findOne();
        assertThat(invalidUser1).isNull();
        assertThat(response1.getStatus()).isEqualTo(422);
        assertThat(response1.getBody()).contains("Имя не должно быть пустым");



        HttpResponse<String> response2 = Unirest
                .post(baseUrl + "/users")
                .field("firstName", "Pavel")
                .field("lastName", "")
                .field("email", "pv-paul@mail.ru")
                .field("password", "vaspav")
                .asString();
        User invalidUser2 = new QUser()
                .email.equalTo("pv-paul@mail.ru")
                .findOne();
        assertThat(invalidUser2).isNull();
        assertThat(response2.getStatus()).isEqualTo(422);
        assertThat(response2.getBody()).contains("Фамилия не должна быть пустой");

        HttpResponse<String> response3 = Unirest
                .post(baseUrl + "/users")
                .field("firstName", "Pavel")
                .field("lastName", "Vasilev")
                .field("email", "pv-paulmail.ru")
                .field("password", "vaspav")
                .asString();
        User invalidUser3 = new QUser()
                .email.equalTo("pv-paulmail.ru")
                .findOne();
        assertThat(invalidUser3).isNull();
        assertThat(response3.getStatus()).isEqualTo(422);
        assertThat(response3.getBody()).contains("Должно быть валидным email");

        HttpResponse<String> response4 = Unirest
                .post(baseUrl + "/users")
                .field("firstName", "Pavel")
                .field("lastName", "Vasilev")
                .field("email", "pv-paul@mail.ru")
                .field("password", "va")
                .asString();
        User invalidUser4 = new QUser()
                .email.equalTo("pv-paul@mail.ru")
                .findOne();
        assertThat(invalidUser4).isNull();
        assertThat(response4.getStatus()).isEqualTo(422);
        assertThat(response4.getBody()).contains("Пароль должен содержать не менее 4 символов");
    }
    // END
}

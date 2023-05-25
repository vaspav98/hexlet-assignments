package exercise.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Map;

import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.Files;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.lang3.ArrayUtils;

public class UsersServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
                throws IOException, ServletException {

        String pathInfo = request.getPathInfo();

        if (pathInfo == null) {
            showUsers(request, response);
            return;
        }

        String[] pathParts = pathInfo.split("/");
        String id = ArrayUtils.get(pathParts, 1, "");

        showUser(request, response, id);
    }

    private List getUsers() throws JsonProcessingException, IOException {
        // BEGIN
        Path fileWithUsersPath = Paths.get("src", "main", "resources", "users.json")
                .toAbsolutePath().normalize();
        String json = Files.readString(fileWithUsersPath).trim();

        ObjectMapper mapper = new ObjectMapper();
        List<Map<String, String>> users = mapper.readValue(json, new TypeReference<List<Map<String, String>>>() { });
        return users;
        // END
    }

    private void showUsers(HttpServletRequest request,
                          HttpServletResponse response)
                throws IOException {

        // BEGIN
        List<Map<String, String>> users = getUsers();

        StringBuilder body = new StringBuilder();

        body.append("""
                <html>
                    <head>
                    </head>
                    <body>
                        <table>
        """);

        for (Map<String, String> user : users) {
            String id = user.get("id");
            String fullName = user.get("firstName") + " " + user.get("lastName");
            body.append("<tr>" +
                    "<td>" + id + "</td>" +
                    "<td>" +
                        "<a href=\"/users/" + id + "\">" + fullName + "</a>" +
                    "</td>" +
                    "</tr>"
            );
        }

        body.append("""
                        </table>
                    </body>      
                </html>
        """);

        PrintWriter out = response.getWriter();
        out.write(body.toString());
        out.close();
        // END
    }

    private void showUser(HttpServletRequest request,
                         HttpServletResponse response,
                         String id)
                 throws IOException {

        // BEGIN
        List<Map<String, String>> users = getUsers();
        Map<String, String> user = users.stream()
                .filter(x -> x.get("id").equals(id))
                .findFirst()
                .orElse(null);

        if (user == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Not found");
            return;
        }

        String firstName = user.get("firstName");
        String lastName = user.get("lastName");
        String email = user.get("email");

        StringBuilder body = new StringBuilder();
        body.append("""
                <html>
                    <head>
                    </head>
                    <body>
                        <table>
        """);

        body.append("<tr>" +
                "<td>" + "firstName" + "</td>" +
                "<td>" + firstName + "</td>" +
                "</tr>" +
                "<tr>" +
                "<td>" + "lastName" + "</td>" +
                "<td>" + lastName + "</td>" +
                "</tr>" +
                "<tr>" +
                "<td>" + "id" + "</td>" +
                "<td>" + id + "</td>" +
                "</tr>" +
                "<tr>" +
                "<td>" + "email" + "</td>" +
                "<td>" + email + "</td>" +
                "<tr>"
        );

        body.append("""
                        </table>
                    </body>      
                </html>
        """);

        PrintWriter out = response.getWriter();
        out.write(body.toString());
        out.close();
        // END
    }
}

package exercise.servlet;

import exercise.Data;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.stream.Collectors;
import static exercise.Data.getCompanies;

public class CompaniesServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
                throws IOException, ServletException {

        // BEGIN
        List<String> companies = Data.getCompanies();
        PrintWriter out = response.getWriter();

        if (request.getParameter("search") == null || request.getParameter("search").equals("")) {
            companies.forEach(out::println);
            return;
        }

        String filteredCompanies =  companies.stream()
                .filter(company -> company.contains(request.getParameter("search")))
                .collect(Collectors.joining("\n"));

        if (filteredCompanies.length() == 0) {
            out.println("Companies not found");
            return;
        }

        out.println(filteredCompanies);
        // END
    }
}

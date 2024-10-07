package hello.servlet.basic.response;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import hello.servlet.basic.request.HelloData;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "ResponseJsonServlet", urlPatterns = "/response-json")
public class ResponseJsonServlet extends HttpServlet{
    
    private ObjectMapper objectMapper = new ObjectMapper(); 
    
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("utf-8");

        HelloData helloData = new HelloData();
        helloData.setUsername("kim");
        helloData.setAge(345);

        // Convert the helloData object into a JSON-formatted string
        String result = objectMapper.writeValueAsString(helloData); 
        resp.getWriter().write(result);

    }
}

package hello.servlet.basic.request;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



@WebServlet(name = "RequestParamServlet", urlPatterns = "/request-param")
public class RequestParamServlet extends HttpServlet{
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("RequestParamServlet.service()");
        System.out.println("[Get all parameters] - start");
        req.getParameterNames().asIterator().forEachRemaining(paramName -> System.out.println(paramName + "=" + req.getParameter(paramName))); // dictionary
        System.out.println("[Get all parameters] - end ");
        System.out.println();

        System.out.println("[get one param] - start");
        
        String username = req.getParameter("username");
        String age = req.getParameter("age");
        
        System.out.println("username = " + username);
        System.out.println("age = " + age);

        System.out.println("[get one param] - end");

        System.out.println();

        System.out.println("[get values on name param] - start");

        String[] userNames = req.getParameterValues("username"); // returns array
        for(String name: userNames){
            System.out.println("user name: " + name);
        }

        System.out.println("[get values on name param] - end");

        resp.getWriter().write("OK");
    }
    
}

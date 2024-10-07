package hello.servlet.basic;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;

@WebServlet(name = "helloServlet", urlPatterns = "/hello")
public class HelloServlet extends HttpServlet{
    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        // TODO Auto-generated method stub
        System.out.println("HelloServlet.service()");  
        System.out.println("request: " + req);
        System.out.println("response: " + res);

        String username = req.getParameter("username");
        System.out.println("username: "+username); 

        
        res.setContentType("text/plain ");
        res.setCharacterEncoding("utf-8");
        res.getWriter().write("Hello " + username); // so html is generated automatically. cool
    }
    
}

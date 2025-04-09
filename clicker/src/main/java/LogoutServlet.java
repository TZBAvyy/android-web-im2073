import java.io.*;

import jakarta.servlet.*;            // Tomcat 10 (Jakarta EE 9)
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    // FOR GET requests on webapp side:
    // localhost:9999/logout?web=true
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("\nGET Request to /logout");
        final String REQUEST_TYPE = req.getParameter("web");
        if (REQUEST_TYPE == null) {
            System.out.println("User logged out from mobile app");
            resp.setStatus(HttpServletResponse.SC_OK);
        } else {
            System.out.println("User logged out from web app");
            req.getSession().invalidate(); // Invalidate the session to log out the user
            resp.sendRedirect("/quiz");
        }
    }
}

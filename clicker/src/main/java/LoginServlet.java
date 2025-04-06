import java.io.*;

import jakarta.servlet.*;            // Tomcat 10 (Jakarta EE 9)
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("\nGET Request to /login");
        // Redirect to the login page if accessed via GET method
        req.getRequestDispatcher("/loginform.jsp").forward(req, resp);
        System.out.println("Redirecting to loginform.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("\nPOST Request to /login");

        // CHECKING IF USER IS ALREADY LOGGED IN
        if (req.getSession(false).getAttribute("user") != null) {
            System.out.println("User already logged in, redirecting to home page");
            resp.sendRedirect("/quiz");
            return;
        }

        // PARAMETER CHECKING
        if (req.getParameter("email") == null || req.getParameter("password") == null) {
            System.out.println("Missing email or password parameter detected");
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing email or password parameter");
            return;
        } 
        final String EMAIL_PARAM = req.getParameter("email");
        final String PASSWORD_PARAM = req.getParameter("password");
        System.out.println("Parameters: email=" + EMAIL_PARAM + ", password=Not telling lmao");

        // USER AUTHENTICATION
        final User user = User.findUser(EMAIL_PARAM);
        if (user == null) {
            System.out.println("User not found");
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "User not found");
            return;
        } else if (!user.checkPassword(PASSWORD_PARAM)) {
            System.out.println("Incorrect password for user: " + user.getName());
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Incorrect password for user: " + user.getName());
            return;
        } else {
            System.out.println("User found: " + user.getName());
        }
        
        // SETTING ATTRIBUTES FOR JSP
        HttpSession session = req.getSession(true);
        session.setAttribute("username", user.getName());
        session.setAttribute("useremail", user.getEmail());
        if (req.getParameter("web") == null) {
            System.out.println("User logged in from mobile app");
            resp.setStatus(HttpServletResponse.SC_OK);
        } else {
            System.out.println("User logged in from web app");
            req.getSession(true).setAttribute("user", user);
            resp.sendRedirect("/quiz");
        }
        System.out.println("Login successful");
    }
}

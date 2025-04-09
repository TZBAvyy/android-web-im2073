import java.io.*;
import java.util.ArrayList;

import jakarta.servlet.*;            // Tomcat 10 (Jakarta EE 9)
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("\nGET Request to /login");
        resp.sendRedirect("/quiz");
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
        if (user == null || !user.checkPassword(PASSWORD_PARAM)) {
            String error = "Incorrect email or password";
            System.out.println(error);
            if (req.getParameter("web") == null) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, error);
            } else {
                req.setAttribute("error", error);
                req.getRequestDispatcher("index.jsp").include(req, resp);
            }
            return;
        } else {
            System.out.println("User found: " + user.getName());
        }

        // FIND ROOMS FOR USER
        final ArrayList<Room> ROOMS = Room.getUserRooms(user.getId());
        
        // SETTING ATTRIBUTES FOR JSP
        HttpSession session = req.getSession(true);
        session.setAttribute("username", user.getName());
        session.setAttribute("useremail", user.getEmail());
        session.setAttribute("rooms", ROOMS);
        if (req.getParameter("web") == null) {
            System.out.println("User logged in from mobile app");
            resp.setStatus(HttpServletResponse.SC_OK);
        } else {
            System.out.println("User logged in from web app");
            resp.sendRedirect("home.jsp");
        }
        System.out.println("Login successful");
    }
}

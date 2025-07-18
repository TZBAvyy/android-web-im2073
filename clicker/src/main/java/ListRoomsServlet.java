import java.io.*;
import java.util.ArrayList;

import jakarta.servlet.*;            // Tomcat 10 (Jakarta EE 9)
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/home")
public class ListRoomsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("\nGET Request to /home");

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("userid") == null) {
            System.out.println("User not logged in, redirecting to login page");
            resp.sendRedirect("/quiz");
            return;
        }
        System.out.println("User found, user ID: " + session.getAttribute("userid"));

        final int USER_ID = (int) session.getAttribute("userid");
        final ArrayList<Room> ROOM_ARRAY = Room.getUserRooms(USER_ID);
        System.out.println("Rooms found: " + ROOM_ARRAY.size());
        for (Room room : ROOM_ARRAY) {
            System.out.println("Room ID: " + room.getId() + ", Code: " + room.getRoomCode());
        }
        req.setAttribute("rooms", ROOM_ARRAY); 
        req.getRequestDispatcher("home.jsp").include(req, resp);
    }
}

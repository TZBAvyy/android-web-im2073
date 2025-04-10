import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import jakarta.servlet.*;            // Tomcat 10 (Jakarta EE 9)
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/start")
public class StartGameServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("\nPOST Request to /start");

        // Check POST parameters
        final String ROOM_ID = req.getParameter("room_id");
        if (ROOM_ID == null) {
            System.out.println("Missing room_id parameter");
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing room_id parameter");
            return;
        }
        System.out.println("Room ID retrieved from GET params: " + ROOM_ID);

        // Check if user is logged in
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("userid") == null) {
            System.out.println("User not logged in, redirecting to login page");
            resp.sendRedirect("/quiz");
            return;
        }
        System.out.println("User logged in, user ID: " + session.getAttribute("userid"));

        // Check if the room exists
        final Room ROOM = Room.getRoom(Integer.parseInt(ROOM_ID));
        if (ROOM == null) {
            System.out.println("Room not found");
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Room not found");
            return;
        }
        System.out.println("Room found and set");

        // Check for questions in room
        final ArrayList<Question> QUESTIONS = Question.getRoomQuestions(Integer.parseInt(ROOM_ID));
        if (QUESTIONS == null || QUESTIONS.isEmpty()) {
            System.out.println("No questions found for room " + ROOM_ID);
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "No questions found for room " + ROOM_ID);
            return;
        }
        System.out.println("Questions found for room");

        // Create array of question IDs that have been displayed
        ArrayList<Integer> questionIds = new ArrayList<>();
        questionIds.add(QUESTIONS.get(0).getId());
        System.out.println("First question set to: " + QUESTIONS.get(0).getQuestion_text());

        // Update current_question to the first question in the room
        final DBProperties dbProps = new DBProperties();
        final String sqlStatement = """
                update roomsessions
                set current_question = ?
                where id = ?;
                """;
        try(
            Connection conn = DriverManager.getConnection(dbProps.url, dbProps.user, dbProps.password);
            PreparedStatement stmt = conn.prepareStatement(sqlStatement);
        ) {
            stmt.setInt(1, questionIds.get(0));
            stmt.setInt(2, Integer.parseInt(ROOM_ID));
            stmt.executeUpdate();
            System.out.println("Current question updated in database");

            session.setAttribute("current_room", ROOM);
            session.setAttribute("questions_done", questionIds);
            System.out.println("Session attributes set, redirecting to first question");

            resp.sendRedirect("display?question_id=" + questionIds.get(0));
        } catch (SQLException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error: " + e.getMessage());
            return;
        }
    }
}

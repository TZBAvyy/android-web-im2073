import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import jakarta.servlet.*;            // Tomcat 10 (Jakarta EE 9)
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/display")
public class DisplayQuestionServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("\nGET Request to /display");

         // Display a all questions in room if ?room_id=... is provided
        if (req.getParameter("room_id") != null) { 
            System.out.println("room_id found in GET Request");
            final int ROOM_ID = Integer.parseInt(req.getParameter("room_id"));
            final Room ROOM = Room.getRoom(ROOM_ID);
            if (ROOM == null) {
                System.out.println("Room not found");
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Room not found");
                return;
            }
            System.out.println(ROOM);
            req.setAttribute("room_id", ROOM.getId());
            req.setAttribute("room_code", ROOM.getRoomCode());
            final ArrayList<Question> questions = Question.getRoomQuestions(ROOM_ID);
            System.out.println("Questions: " + questions);
            req.setAttribute("questions", questions);
            req.getRequestDispatcher("/list_questions.jsp").include(req, resp);
        
        // Display a specific question if ?question_id=... is provided and room_id is not provided
        } else if (req.getParameter("question_id") != null) {
            System.out.println("question_id found in GET Request");
            final int QUESTION_ID = Integer.parseInt(req.getParameter("question_id"));
            final Question question = Question.getQuestion(QUESTION_ID);
            if (question == null) {
                System.out.println("Question not found");
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Question not found");
                return;
            }
            System.out.println("Question: " + question);
            req.setAttribute("question", question);
            req.getRequestDispatcher("/display_question.jsp").include(req, resp);

        // Display an error if neither room_id nor question_id is provided
        } else {
            System.out.println("Missing room_id and question_id parameter");
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing room_id and question_id parameter");
            return;
        }

        System.out.println("Request sent successfully");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("\nPOST Request to /display");

        // Check if session exists and user is logged in
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("userid") == null) {
            System.out.println("User not logged in, redirecting to login page");
            resp.sendRedirect("/quiz");
            return;
        }
        System.out.println("User logged in, user ID: " + session.getAttribute("userid"));

        // Get session attributes
        final Room ROOM = (Room) session.getAttribute("current_room");
        final int QUESTION_NUMBER = (int) session.getAttribute("question_no") + 1;

        // Check for the questions in room
        final ArrayList<Question> QUESTIONS = Question.getRoomQuestions(ROOM.id);
        if (QUESTIONS == null || QUESTIONS.isEmpty()) {
            System.out.println("No questions found for room " + ROOM.id);
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "No questions found for room " + ROOM.id);
            return;
        }
        System.out.println("Questions found for room");

        // Check if the next question exists
        boolean questionInRange = (QUESTION_NUMBER < QUESTIONS.size());

        // Update current_question to the next question in the room
        final DBProperties dbProps = new DBProperties();
        String sqlStatement = "";
        if (questionInRange) {
            sqlStatement = """
                update roomsessions
                set current_question = ?
                where id = ?;
                """;
        } else {
            sqlStatement = """
                update roomsessions
                set is_open = 0, current_question = null
                where id = ?;
                """;
        }
        try(
            Connection conn = DriverManager.getConnection(dbProps.url, dbProps.user, dbProps.password);
            PreparedStatement stmt = conn.prepareStatement(sqlStatement);
        ) {
            if (questionInRange) {
                stmt.setInt(1, QUESTIONS.get(QUESTION_NUMBER).getId());
                stmt.setInt(2, ROOM.id);
                stmt.executeUpdate();
                System.out.println("Current question updated in database");
                ROOM.current_question_id = QUESTIONS.get(QUESTION_NUMBER).getId();

                // Update session attributes
                session.setAttribute("current_room", ROOM);
                session.setAttribute("question_no", QUESTION_NUMBER);
                System.out.println("Session attributes set, redirecting to first question");

                resp.sendRedirect("display?question_id=" + QUESTIONS.get(QUESTION_NUMBER).getId());
            } else {
                stmt.setInt(1, ROOM.id);
                stmt.executeUpdate();
                System.out.println("IsOpen updated in database");
                System.out.println("Game ended, no more questions left");

                // Update session attributes
                session.removeAttribute("current_room");
                session.removeAttribute("question_no");
                System.out.println("Session attributes removed, redirecting to home page");

                resp.sendRedirect("/quiz/home");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error: " + e.getMessage());
            return;
        }
    }
}

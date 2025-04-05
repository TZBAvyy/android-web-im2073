import java.io.*;
import java.util.ArrayList;

import jakarta.servlet.*;            // Tomcat 10 (Jakarta EE 9)
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/display")
public class DisplayQuestionServlet extends HttpServlet {
    // REQUIRES ?room_id=... inside the URL

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("\nGET Request to /display");

        // PARAMETER CHECKING
        if (req.getParameter("room_id") == null) {
            System.out.println("Missing room_id parameter detected");
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing room_id parameter");
            return;
        } 
        final int ROOM_ID = Integer.parseInt(req.getParameter("room_id"));
        System.out.print("Parameters: room_id=" + ROOM_ID);

        final String QUESTION_NUMBER_PARAMETER = req.getParameter("question_no");
        if (QUESTION_NUMBER_PARAMETER != null) {
            System.out.print(", question_no=" + QUESTION_NUMBER_PARAMETER);
        }
        
        // FETCHING ROOM QUESTIONS
        final ArrayList<Question> questions = Question.getRoomQuestions(ROOM_ID);
        if (questions == null) {
            System.out.println("Room not found or no questions available");
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Room not found or no questions available");
            return;
        }
        System.out.println("Questions: " + questions);

        // SETTING ATTRIBUTES FOR JSP
        req.setAttribute("room_id", ROOM_ID);
        if (QUESTION_NUMBER_PARAMETER != null) {
            // Display a specific question if ?question_no=... is provided
            final Question question = questions.get(Integer.parseInt(QUESTION_NUMBER_PARAMETER));
            req.setAttribute("question", question);
            req.setAttribute("question_no", QUESTION_NUMBER_PARAMETER);
            req.getRequestDispatcher("/display_question.jsp").include(req, resp);
        } else {
            // Display the list of questions if no question_no is provided
            req.setAttribute("questions", questions);
            req.getRequestDispatcher("/list_questions.jsp").include(req, resp);
        }
        System.out.println("Request sent successfully");
    }
}

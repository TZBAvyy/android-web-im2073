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
        if (req.getParameter("room_id") == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing room_id parameter");
            return;
        } 

        final int ROOM_ID = Integer.parseInt(req.getParameter("room_id"));
        final String QUESTION_NUMBER_PARAMETER = req.getParameter("question_no");
        final ArrayList<Question> questions = Question.getRoomQuestions(ROOM_ID);

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
    }
}

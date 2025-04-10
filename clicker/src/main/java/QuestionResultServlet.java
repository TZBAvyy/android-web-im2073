import java.io.*;
import java.util.ArrayList;

import jakarta.servlet.*;            // Tomcat 10 (Jakarta EE 9)
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/result")
public class QuestionResultServlet extends HttpServlet {
    // GET FORMAT: result?question_id=${question.id}
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("\nGET Request to /result");

        HttpSession session = req.getSession(false);
        final String QUESTION_ID = req.getParameter("question_id");
        if (session == null || QUESTION_ID == null) {
            System.out.println("Something went wrong (session or param), redirecting to /quiz");
            resp.sendRedirect("/quiz");
            return;
        }

        Question question = Question.getQuestion(Integer.parseInt(QUESTION_ID));
        ArrayList<Response> responses = Response.getResponses(Integer.parseInt(QUESTION_ID));
        int totalResponses = responses.size();
        System.out.println("Responses [" + totalResponses + "]");
        for (Response response : responses) {
            System.out.println(response);
        }
        
        req.setAttribute("question", question);
        req.setAttribute("responses", responses);
        req.setAttribute("totalResponses", totalResponses);
        req.getRequestDispatcher("question_result.jsp").include(req, resp);
    }
}

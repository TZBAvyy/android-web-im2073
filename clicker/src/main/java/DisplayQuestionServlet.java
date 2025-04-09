import java.io.*;
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
}

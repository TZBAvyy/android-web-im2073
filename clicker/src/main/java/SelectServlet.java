import java.io.*;
import java.sql.*;

import jakarta.servlet.*;            // Tomcat 10 (Jakarta EE 9)
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/select")
public class SelectServlet extends HttpServlet {
    // TODO: FOR NOW, /select will insert through GET request instead of POST
    //  PARAMETERS REQUIRED:
    //  - question_id (int) [ Question table contains room_id ]
    //  - player_id (int)
    //  - choice (char)

    // TEST: curl -X GET "http://localhost:9999/quiz/select?question_id=2&player_id=1&choice=A" [FAIL]
    // TEST: curl -X GET "http://localhost:9999/quiz/select?question_id=1&player_id=1&choice=B" [SUCCESS]

    // Possible Errors:
    //  - Missing parameters (room_id, question_no, player_id, choice) DONE
    //  - Question not found (question_id) DONE
    //  - Player not found (player_id)
    //  - Invalid choice (choice)
    //  - Repeated response (player_id + question_id combination exists in reponses table) DONE
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("\nGET Request to /select");
        final String QUESTION_ID_PARAMETER = req.getParameter("question_id");
        final String PLAYER_ID_PARAMETER = req.getParameter("player_id");
        final String CHOICE_PARAMETER = req.getParameter("choice");

        final boolean isParametersNull = (QUESTION_ID_PARAMETER == null || PLAYER_ID_PARAMETER == null || CHOICE_PARAMETER == null);
        if (isParametersNull) {
            System.out.println("Missing parameters detected");
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing parameters");
            return;
        }

        final int PLAYER_ID = Integer.parseInt(PLAYER_ID_PARAMETER);
        final int QUESTION_ID = Integer.parseInt(QUESTION_ID_PARAMETER);
        final char CHOICE = CHOICE_PARAMETER.charAt(0);
        System.out.println("Parameters: question_id=" + QUESTION_ID + ", player_id=" + PLAYER_ID + ", choice=" + CHOICE);

        final Question question = Question.getQuestion(QUESTION_ID);
        System.out.println("Question: " + question);
        if (question == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Question not found");
            return;
        }

        if (Response.exists(PLAYER_ID, QUESTION_ID)) {
            System.out.println("Response exists for this player and question already");
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Response already exists");
            return;
        }

        // TODO: check if Question.answer == Response.choice => if true, player.score += 1

        // INSERTION DATABASE CONNECTION (CREATE NEW RESPONSE)
        System.out.println("Inserting response into database...");
        final DBProperties dbProps = new DBProperties();
        final String sqlStatement = """
                insert into responses (player_id, question_id, choice) values (?, ?, ?);  
                """;
        try(
            Connection conn = DriverManager.getConnection(dbProps.url, dbProps.user, dbProps.password);
            PreparedStatement stmt = conn.prepareStatement(sqlStatement);
        ) {
            stmt.setInt(1, PLAYER_ID);
            stmt.setInt(2, QUESTION_ID);
            stmt.setString(3, String.valueOf(CHOICE));
            stmt.executeUpdate();
            System.out.println("Response inserted successfully");
        } catch (SQLException e) {
            System.out.println("Error inserting response into database: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

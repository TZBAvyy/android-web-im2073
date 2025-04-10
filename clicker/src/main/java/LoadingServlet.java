import java.io.*;
import java.sql.*;

import jakarta.servlet.*;            // Tomcat 10 (Jakarta EE 9)
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/loading_check")
public class LoadingServlet extends HttpServlet {
    // POST Method: for android checking if the room is loading
    // params:
    // - player_id: Integer, the id of the player record of the user in the room
    // - question_id: Integer, the id of the question record of the user in the room
    // response output: (on 200)
    // - current_question: Integer, the FK id of the current question in the room
    // - game_end: Boolean, true if the game has ended, false otherwise

    // TEST: curl --data "player_id=1&question_id=1" localhost:9999/quiz/loading_check
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("\nPOST Request to /loading_check");

        final String PLAYER_ID = req.getParameter("player_id");
        final String QUESTION_ID = req.getParameter("question_id");
        if (PLAYER_ID == null || QUESTION_ID == null) {
            System.out.println("Missing player_id or question_id parameter detected");
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing player_id parameter");
            return;
        }
        System.out.println("Parameters: player_id=" + PLAYER_ID + ", question_id=" + QUESTION_ID);

        final DBProperties dbProps = new DBProperties();
        final String sqlStatement = """
                select roomsessions.is_open, roomsessions.current_question 
                from roomsessions 
                inner join players on players.room_id = roomsessions.id 
                where players.id=?; 
                """;
        try(
            Connection conn = DriverManager.getConnection(dbProps.url, dbProps.user, dbProps.password);
            PreparedStatement stmt = conn.prepareStatement(sqlStatement);
        ) {
            stmt.setInt(1, Integer.parseInt(PLAYER_ID));
            ResultSet result = stmt.executeQuery();
            if (result.next()) {
                boolean isOpen = result.getBoolean("is_open");
                int currentQuestion = result.getInt("current_question");
                if (currentQuestion == Integer.parseInt(QUESTION_ID)) {
                    System.out.println("Host is still on question/result page, waiting for next question to be loaded");
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                } else {
                    System.out.println("Host has moved to next question, sending response");
                    resp.setStatus(HttpServletResponse.SC_OK);
                    resp.setContentType("application/json");
                    resp.getWriter().write("{\"game_end\": " + !isOpen + ", \"current_question\": " + currentQuestion + "}");
                }
            } else {
                System.out.println("No room session found for player_id: " + PLAYER_ID);
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "No room session found for player_id: " + PLAYER_ID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error: " + e.getMessage());
            return;
        }
        
    }
}

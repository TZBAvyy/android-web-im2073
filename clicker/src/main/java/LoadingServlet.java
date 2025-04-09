import java.io.*;

import jakarta.servlet.*;            // Tomcat 10 (Jakarta EE 9)
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/loading_check")
public class LoadingServlet extends HttpServlet {
    // POST Method: for android checking if the room is loading
    // params:
    // - player_id: Integer, the id of the player record of the user in the room
    // response output: (on 200)
    // - current_question: Integer, the FK id of the current question in the room
    // - game_end: Boolean, true if the game has ended, false otherwise
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
    }
}

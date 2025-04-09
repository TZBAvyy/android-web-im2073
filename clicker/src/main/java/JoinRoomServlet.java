import java.io.*;

import jakarta.servlet.*;            // Tomcat 10 (Jakarta EE 9)
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/join_room")
public class JoinRoomServlet extends HttpServlet {
    // POST Method: for android joining a room
    // params:
    // - room_code: String of length 6, the room code to join
    // - user_id: Integer, the user id of the user joining the room
    // response output: (on 200)
    // - player_id: Integer, the id of the newly created player record of the user in the room
    // - current_question: Integer, the FK id of the current question in the room
    // - question_interval: Integer, the interval of the current question in seconds
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
    }
}

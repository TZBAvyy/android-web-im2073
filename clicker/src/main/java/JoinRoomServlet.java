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
    // if it's a valid room code, check if it's opened
    // response output: (on 200)
    // - player_id: Integer, the id of the newly created player record of the user in the room
    // - current_question: Integer, the FK id of the current question in the room
    // - question_interval: Integer, the interval of the current question in seconds
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String roomCode = req.getParameter("roomCode");
        int userId = Integer.parseInt(req.getParameter("userId"));

        System.out.println("Received JoinRoom POST: userId = " + userId + ", roomCode = " + roomCode);

        Integer roomId = Room.getRoomId(roomCode);
        System.out.println("Room ID: " + roomId);

        if (roomId == null) {
            String error = "Invalid room code";
            System.out.println(error);
            if (req.getParameter("web") == null) {
                // respond with JSON 
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.setContentType("application/json");
                resp.getWriter().write("{\"error\": \"" + error + "\"}");
            }
            return;
        }

        Room room = Room.getRoom(roomId);

        if (room == null) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.setContentType("application/json");
            resp.getWriter().write("{\"error\": \"Room data could not be retrieved\"}");
            return;
        }
        
        if (room.isOpen()) {
            Integer playerId = Room.joinRoom(roomId, userId);
            if (req.getParameter("web") == null) {
                System.out.println("User joined from mobile app");
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.setContentType("application/json");
                resp.getWriter().write("{" +
                            "\"roomId\":" + roomId + "," +
                            "\"playerId\":" + playerId + "," +
                            "\"current_question\":" + room.getCurrentQuestionId() + "}");
            }
            System.out.println("Joined room: Room ID: " + roomId + ", Player ID: " + playerId);
        } else {
            String error = "Room not opened";
            System.out.println(error);
            if (req.getParameter("web") == null) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.setContentType("application/json");
                resp.getWriter().write("{\"error\": \"" + error + "\"}");
            }
            return;
        }
    }
}

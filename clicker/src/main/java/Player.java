import java.sql.*;
import java.util.ArrayList;

public class Player {
    int id;
    int room_id;
    int user_id;
    int score;

    public Player(int id, int room_id, int user_id, int score) {
        this.id = id;
        this.room_id = room_id;
        this.user_id = user_id;
        this.score = score;
    }

    public int getId() {
        return id;
    }
    public int getRoom_id() {
        return room_id;
    }
    public int getUser_id() {
        return user_id;
    }
    public int getScore() {
        return score;
    }
    
}

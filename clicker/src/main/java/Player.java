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
    public ArrayList<Response> getResponses() {
        final DBProperties dbProps = new DBProperties();
        final String sqlStatement = """
                select * from responses where player_id=?; 
                """;
        try(
            Connection conn = DriverManager.getConnection(dbProps.url, dbProps.user, dbProps.password);
            PreparedStatement stmt = conn.prepareStatement(sqlStatement);
        ) {
            stmt.setInt(1, this.id);
            ResultSet result = stmt.executeQuery();

            ArrayList<Response> responses = new ArrayList<>();
            while (result.next()) {
                Response response = new Response(
                        result.getInt("id"),
                        this.id,
                        result.getInt("question_id"),
                        result.getString("choice").charAt(0)
                );
                responses.add(response);
            }
            return responses;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}

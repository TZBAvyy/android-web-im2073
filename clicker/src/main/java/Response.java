import java.sql.*;

public class Response {
    int id;
    int player_id;
    int question_id;
    char choice;

    public Response(int id, int player_id, int question_id, char choice) {
        this.id = id;
        this.player_id = player_id;
        this.question_id = question_id;
        this.choice = choice;
    }

    public int getId() {
        return id;
    }
    public int getPlayer_id() {
        return player_id;
    }
    public int getQuestion_id() {
        return question_id;
    }
    public char getChoice() {
        return choice;
    }

    // STATIC METHODS
    public static boolean exists(int player_id, int question_id) {
        // Check if the response already exists in the database
        final DBProperties dbProps = new DBProperties();
        final String sqlStatement = """
                select * from responses where question_id=? and player_id=?; 
                """;
        try(
            Connection conn = DriverManager.getConnection(dbProps.url, dbProps.user, dbProps.password);
            PreparedStatement stmt = conn.prepareStatement(sqlStatement);
        ) {
            stmt.setInt(1, question_id);
            stmt.setInt(2, player_id);
            ResultSet result = stmt.executeQuery();
            return result.next(); // If a row is returned, the response exists
        } catch (SQLException e) {
            e.printStackTrace();
            return true; // If there's an error, assume the response exists to avoid duplicates
        } 
    }
}

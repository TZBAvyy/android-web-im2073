import java.sql.*;
import java.util.ArrayList;

public class Response {
    int id;
    char choice;
    String player_name;
    String question_text;

    public Response(int id, char choice, String player_name, String question_text) {
        this.id = id;
        this.choice = choice;
        this.player_name = player_name;
        this.question_text = question_text;
    }

    public int getId() {
        return id;
    }
    public char getChoice() {
        return choice;
    }
    public String getPlayer_name() {
        return player_name;
    }
    public String getQuestion_text() {
        return question_text;
    }

    @Override
    public String toString() {
        return "Response[" +
                "id=" + id +
                ", choice=" + choice +
                ", player_name=" + player_name +
                ", question_text=" + question_text  +
                ']';
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

    public static ArrayList<Response> getResponses(int question_id) {
        // Get all responses for a specific question
        final DBProperties dbProps = new DBProperties();
        final String sqlStatement = """
                select responses.id, questions.question_text, responses.choice, users.name
                from responses 
                inner join players on responses.player_id = players.id
                inner join users on players.user_id = users.id
                inner join questions on responses.question_id = questions.id
                where question_id=?; 
                """;
        ArrayList<Response> responses = new ArrayList<>();
        try(
            Connection conn = DriverManager.getConnection(dbProps.url, dbProps.user, dbProps.password);
            PreparedStatement stmt = conn.prepareStatement(sqlStatement);
        ) {
            stmt.setInt(1, question_id);
            ResultSet result = stmt.executeQuery();
            while (result.next()) {
                responses.add(new Response(
                    result.getInt("id"), 
                    result.getString("choice").charAt(0),
                    result.getString("name"),
                    result.getString("question_text")
                    ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return responses;
    }
}

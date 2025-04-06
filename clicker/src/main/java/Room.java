import java.sql.*;

public class Room {
    int id;
    String room_code;
    int max_capacity;
    int owner_id;
    boolean isOpen;
    int questionInterval;
    int current_question_id;

    public Room(int id, String room_code, int max_capacity, int owner_id, boolean isOpen, int questionInterval, int current_question_id) {
        this.id = id;
        this.room_code = room_code;
        this.max_capacity = max_capacity;
        this.owner_id = owner_id;
        this.isOpen = isOpen;
        this.questionInterval = questionInterval;
        this.current_question_id = current_question_id;
    }
    public int getId() {
        return id;
    }
    public String getRoomCode() {
        return room_code;
    }
    public int getMaxCapacity() {
        return max_capacity;
    }
    public int getOwnerId() {
        return owner_id;
    }
    public boolean isOpen() {
        return isOpen;
    }
    public int getQuestionInterval() {
        return questionInterval;
    }
    public int getCurrentQuestionId() {
        return current_question_id;
    }

    // STATIC METHODS
    public static Room getRoom(int roomId) {
        final DBProperties dbProps = new DBProperties();
        final String sqlStatement = """
                select * from roomsessions where id=?; 
                """;
        try(
            Connection conn = DriverManager.getConnection(dbProps.url, dbProps.user, dbProps.password);
            PreparedStatement stmt = conn.prepareStatement(sqlStatement);
        ) {
            stmt.setInt(1, roomId);
            ResultSet result = stmt.executeQuery();
            if (result.next()) {
                return new Room(
                        result.getInt("id"),
                        result.getString("room_code"),
                        result.getInt("max_capacity"),
                        result.getInt("owner_id"),
                        result.getBoolean("is_open"),
                        result.getInt("question_interval"),
                        result.getInt("current_question")
                );
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

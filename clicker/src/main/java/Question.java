import java.sql.*;
import java.util.ArrayList;

public class Question {
    int id;
    int room_id;
    String question_text;
    char answer;
    String[] choices = new String[4];

    public Question(int id, int room_id, String question_text, char answer, String[] choices) {
        this.id = id;
        this.room_id = room_id;
        this.question_text = question_text;
        this.answer = answer;
        this.choices = choices;
    }

    public int getId() {
        return id;
    }
    public int getRoom_id() {
        return room_id;
    }
    public String getQuestion_text() {
        return question_text;
    }
    public char getAnswer() {
        return answer;
    }
    public String getChoice_one() {
        return choices[0];
    }
    public String getChoice_two() {
        return choices[1];
    }
    public String getChoice_three() {
        return choices[2];
    }
    public String getChoice_four() {
        return choices[3];
    }
    @Override
    public String toString() {
        return "Question[" +
                "id=" + id +
                ", room_id=" + room_id +
                ", question_text='" + question_text + '\'' +
                ", answer=" + answer +
                ", choices=" + String.join(", ", choices) +
                ']';
    }

    // STATIC METHODS
    public static ArrayList<Question> getRoomQuestions(int room_id) {
        final DBProperties dbProps = new DBProperties();
        final String sqlStatement = """
                select * from questions where room_id=?; 
                """;
        try(
            Connection conn = DriverManager.getConnection(dbProps.url, dbProps.user, dbProps.password);
            PreparedStatement stmt = conn.prepareStatement(sqlStatement);
        ) {
            stmt.setInt(1, room_id);
            ResultSet result = stmt.executeQuery();

            ArrayList<Question> questions = new ArrayList<>();
            while (result.next()) {
                String[] choices = new String[4];
                choices[0] = result.getString("choice_one");
                choices[1] = result.getString("choice_two");
                choices[2] = result.getString("choice_three");
                choices[3] = result.getString("choice_four");
                Question question = new Question(
                    result.getInt("id"),
                    room_id,
                    result.getString("question_text"),
                    result.getString("answer").charAt(0),
                    choices
                );
                questions.add(question);
            }
            return questions;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class QuestionTest {
    @Test
    public void testGetRoomQuestions() {
        // Test with a valid room_id
        int room_id = 1; // Assuming this room_id exists in the database
        ArrayList<Question> questions = Question.getRoomQuestions(room_id);
        for (Question question : questions) {
            assertEquals(room_id, question.getRoom_id());
            assertNotNull(question.getQuestion_text());
        }
        assertNotNull(questions);
        assertFalse(questions.isEmpty());

        // Test with an invalid room_id
        room_id = -1; // Assuming this room_id does not exist in the database
        questions = Question.getRoomQuestions(room_id);
        assertNotNull(questions);
        assertTrue(questions.isEmpty());
    }
}

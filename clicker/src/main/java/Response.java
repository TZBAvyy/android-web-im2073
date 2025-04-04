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
}

import java.sql.*;

public class User {
    int id;
    String name;
    String email;
    private String password;

    public User(int id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }
    @Override
    public String toString() {
        return "User[" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ']';
    }

    // STATIC METHODS
    public static User findUser(String email) {
        final DBProperties dbProps = new DBProperties();
        final String sqlStatement = """
                select * from users where email=?; 
                """;
        try(
            Connection conn = DriverManager.getConnection(dbProps.url, dbProps.user, dbProps.password);
            PreparedStatement stmt = conn.prepareStatement(sqlStatement);
        ) {
            stmt.setString(1, email);
            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                User user = new User(
                    result.getInt("id"), 
                    result.getString("name"), 
                    result.getString("email"), 
                    result.getString("password"));
                return user;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}


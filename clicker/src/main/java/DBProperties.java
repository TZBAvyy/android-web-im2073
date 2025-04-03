import java.io.*;
import java.util.Properties;

class DBProperties {
    protected String url, user, password;
    
    public DBProperties() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try (InputStream input = classLoader.getResourceAsStream("database.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            url = prop.getProperty("db.url");
            user = prop.getProperty("db.user");
            password = prop.getProperty("db.password");
        } catch (IOException ex) {
            ex.printStackTrace();
            url = null;
            user = null;
            password = null;
        }
    }
}

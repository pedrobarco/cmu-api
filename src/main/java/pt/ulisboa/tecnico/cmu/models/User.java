package pt.ulisboa.tecnico.cmu.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document(collection = "users")
public class User {

    @Id
    private String id;
    @Indexed(unique = true)
    private String username;
    private String password;
    private String sessionId;
    private int score;

    public User() { }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.sessionId = null;
        this.score = 0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String generateSessionId(){
        String uniqueID = UUID.randomUUID().toString();
        return uniqueID;
    }
}

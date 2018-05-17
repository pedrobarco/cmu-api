package pt.ulisboa.tecnico.cmu.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

@Document(collection = "users")
public class User {

    @Id
    private String id;
    @Indexed(unique = true)
    private String username;
    private Code code;
    private String sessionId;
    private int totalScore;
    private HashMap<String, QuizAnswers> scores = new HashMap<>();
    private HashMap<String, Long> timestamps = new HashMap<>();

    public User() { }

    public User(String username, Code code) {
        this.username = username;
        this.code = code;
        this.sessionId = null;
        this.totalScore = 0;
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

    public Code getCode() {
        return code;
    }

    public void setCode(Code code) {
        this.code = code;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public HashMap<String, QuizAnswers> getScores() {
        return scores;
    }

    public void setScores(HashMap<String, QuizAnswers> scores) {
        this.scores = scores;
    }

    public String generateSessionId(){
        return UUID.randomUUID().toString();
    }

    public HashMap<String, Long> getTimestamps() {
        return timestamps;
    }

    public void setTimestamps(HashMap<String, Long> timestamps) {
        this.timestamps = timestamps;
    }

    public void calcTotalScore(){
        Collection<QuizAnswers> quizAnswers = this.scores.values();
        int totalScore = 0;

        for (QuizAnswers qA : quizAnswers) {
           totalScore += qA.getScore();
        }

        this.totalScore = totalScore;
    }

}

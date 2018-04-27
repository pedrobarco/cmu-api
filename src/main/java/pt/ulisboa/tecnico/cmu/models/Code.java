package pt.ulisboa.tecnico.cmu.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "codes")
public class Code {

    @Id
    private String id;
    @Indexed(unique = true)
    private String secret;
    private String userId;

    public Code() { }

    public Code(String secret) {
        this.secret = secret;
        this.userId = null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

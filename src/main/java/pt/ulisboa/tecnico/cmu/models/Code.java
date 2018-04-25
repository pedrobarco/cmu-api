package pt.ulisboa.tecnico.cmu.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "codes")
public class Code {

    @Id
    private String secret;
    private boolean inUse;

    public Code() { }

    public Code(String secret) {
        this.secret = secret;
        this.inUse = false;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public boolean isInUse() {
        return inUse;
    }

    public void setInUse(boolean inUse) {
        this.inUse = inUse;
    }

}

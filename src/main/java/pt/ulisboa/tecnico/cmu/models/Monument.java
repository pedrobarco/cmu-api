package pt.ulisboa.tecnico.cmu.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "monuments")
public class Monument {

    @Id
    private String ssid;
    private String name;
    private String description;
    private String image;
    private Quiz quiz;

    public Monument() { }

    public Monument(String ssid, String name, String description, String image, List<Quiz> quizList) {
        this.ssid = ssid;
        this.name = name;
        this.description = description;
        this.image = image;
        this.quiz = quiz;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

}

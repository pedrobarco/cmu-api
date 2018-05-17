package pt.ulisboa.tecnico.cmu.models;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "quizzes")
public class Quiz {

    private List<MultipleChoiceQuestion> multipleChoiceQuestionList;
    private List<Integer> solution;
    private Long duration;

    public Quiz(){ }

    public Quiz(List<MultipleChoiceQuestion> multipleChoiceQuestionList, List<Integer> solution, Long duration) {
        this.multipleChoiceQuestionList = multipleChoiceQuestionList;
        this.solution = solution;
        this.duration = duration;
    }

    public List<MultipleChoiceQuestion> getMultipleChoiceQuestionList() {
        return multipleChoiceQuestionList;
    }

    public void setMultipleChoiceQuestionList(List<MultipleChoiceQuestion> multipleChoiceQuestionList) {
        this.multipleChoiceQuestionList = multipleChoiceQuestionList;
    }

    public List<Integer> getSolution() {
        return solution;
    }

    public void setSolution(List<Integer> solution) {
        this.solution = solution;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

}

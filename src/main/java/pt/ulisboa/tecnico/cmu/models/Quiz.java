package pt.ulisboa.tecnico.cmu.models;


import java.util.List;

public class Quiz {

    private List<MultipleChoiceQuestion> multipleChoiceQuestionList;

    public Quiz(){ }

    public Quiz(List<MultipleChoiceQuestion> multipleChoiceQuestionList) {
        this.multipleChoiceQuestionList = multipleChoiceQuestionList;
    }

    public List<MultipleChoiceQuestion> getMultipleChoiceQuestionList() {
        return multipleChoiceQuestionList;
    }

    public void setMultipleChoiceQuestionList(List<MultipleChoiceQuestion> multipleChoiceQuestionList) {
        this.multipleChoiceQuestionList = multipleChoiceQuestionList;
    }
}

package pt.ulisboa.tecnico.cmu.models;


import java.util.List;

public class Quiz {

    private List<MultipleChoiceQuestion> multipleChoiceQuestionList;
    private List<Integer> solution;

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

    public List<Integer> getSolution() {
        return solution;
    }

    public void setSolution(List<Integer> solution) {
        this.solution = solution;
    }
}

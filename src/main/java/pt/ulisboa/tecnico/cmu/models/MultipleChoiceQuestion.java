package pt.ulisboa.tecnico.cmu.models;

import java.util.List;

public class MultipleChoiceQuestion {

    private String question;
    private List<String> answers;
    private Integer answerIndex;

    public MultipleChoiceQuestion(){ }

    public MultipleChoiceQuestion(String question, List<String> answers, Integer answerIndex) {
        this.question = question;
        this.answers = answers;
        this.answerIndex = answerIndex;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    public Integer getAnswerIndex() {
        return answerIndex;
    }

    public void setAnswerIndex(Integer answerIndex) {
        this.answerIndex = answerIndex;
    }

    public String getCorrectAnswer() {
        return this.answers.get(this.answerIndex);
    }
}

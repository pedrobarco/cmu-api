package pt.ulisboa.tecnico.cmu.models;

import java.util.List;

public class QuizAnswers {

    private String monumentId;
    private List<Integer> answers;
    private Long time;
    private List<Integer> solution;
    private Integer score;

    public QuizAnswers(){}

    public QuizAnswers(String monumentId, List<Integer> answers, Long time) {
        this.monumentId = monumentId;
        this.answers = answers;
        this.time = time;
        this.solution = null;
        this.score = 0;
    }

    public String getMonumentId() {
        return monumentId;
    }

    public void setMonumentId(String monumentId) {
        this.monumentId = monumentId;
    }

    public List<Integer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Integer> answers) {
        this.answers = answers;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public List<Integer> getSolution() {
        return solution;
    }

    public void setSolution(List<Integer> solution) {
        this.solution = solution;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public void calcScore(){
        int maxSize = (this.solution.size() >= this.answers.size()) ? solution.size() : this.answers.size();
        int score = 0;

        for (int i = 0; i < maxSize; i++){
            if(this.solution.get(i).equals(this.answers.get(i))){
                score++;
            }
        }

        int extra = (int) (this.time / (60 * 1000));

        this.score = score + extra;
    }

}

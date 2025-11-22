package models;

import java.util.ArrayList;

public class Quiz {
    private static int nextId = 1;
    private int quizId;
    private ArrayList<Question> questions;
    private int passingScore;



    public Quiz(int passingScore) {
        this.quizId = nextId++;
        this.questions = new ArrayList<>();
        this.passingScore = passingScore;
    }

    public int getQuizId() { return quizId; }
    public ArrayList<Question> getQuestions() { return new ArrayList<>(questions); }
    public int getPassingScore() { return passingScore; }

    public void setQuestions(ArrayList<Question> questions) { this.questions = new ArrayList<>(questions); }
    public void setPassingScore(int passingScore) { this.passingScore = passingScore; }

    public void addQuestion(Question question) {
        questions.add(question);
    }

    public int evaluateAnswers(ArrayList<String> studentAnswers) {
        int score = 0;
        for (int i = 0; i < Math.min(questions.size(), studentAnswers.size()); i++) {
            if (questions.get(i).checkAnswer(studentAnswers.get(i))) {
                score += questions.get(i).getPoints();
            }
        }
        return score;
    }

    public boolean isPassed(int score) {
        return score >= passingScore;
    }

    public int getMaxScore() {
        int max = 0;
        for (Question q : questions) {
            max += q.getPoints();
        }
        return max;
    }

}

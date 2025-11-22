package models;

import java.util.ArrayList;

public class Question {
    private String questionText;
    private int points;
    private ArrayList<String> options;
    private String correctAnswer;

    public Question(String questionText, int points, ArrayList<String> options, String correctAnswer) {
        if(questionText == null || options == null || options.isEmpty() || correctAnswer == null)
            throw new IllegalArgumentException("Invalid question data");

        this.questionText = questionText;
        this.points = points;
        this.options = new ArrayList<>(options);
        this.correctAnswer = correctAnswer;
    }

    public String getQuestionText() { return questionText; }
    public int getPoints() { return points; }
    public ArrayList<String> getOptions() { return new ArrayList<>(options); }
    public String getCorrectAnswer() { return correctAnswer; }

    public void setQuestionText(String questionText) { this.questionText = questionText; }
    public void setPoints(int points) { this.points = points; }
    public void setOptions(ArrayList<String> options) { this.options = new ArrayList<>(options); }
    public void setCorrectAnswer(String correctAnswer) { this.correctAnswer = correctAnswer; }

    public boolean checkAnswer(String answer) {
        return correctAnswer.equalsIgnoreCase(answer);
    }

    @Override
    public String toString() {
        return "Question: " + questionText + " | Points: " + points + " | Options: " + options;
    }
}

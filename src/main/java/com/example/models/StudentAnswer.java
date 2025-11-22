package com.example.models;

import com.example.models.Question;

public class StudentAnswer {
    private int studentId;
    private Question question;
    private int selectedOption;

    public StudentAnswer() {}

    public StudentAnswer(int studentId, Question question, int selectedOption){
        this.studentId = studentId;
        this.question = question;
        this.selectedOption = selectedOption;
    }

    public int getStudentId() { return studentId; }
    public Question getQuestion() { return question; }
    public int getSelectedOption() { return selectedOption; }

    public double getScore() {
        return question != null && question.getCorrectAnswer() == selectedOption ? 100 : 0;
    }
}

package com.example.models;

import java.util.ArrayList;
import java.util.List;

public class Quiz {
    private List<Question> questions;
    private List<StudentAnswer> studentAnswers; // لتخزين إجابات الطلاب

    public Quiz() {
        this.questions = new ArrayList<>();
        this.studentAnswers = new ArrayList<>();
    }

    public Quiz(List<Question> questions) {
        this.questions = questions != null ? questions : new ArrayList<>();
        this.studentAnswers = new ArrayList<>();
    }

    public List<Question> getQuestions() { return questions; }
    public void setQuestions(List<Question> questions) { this.questions = questions; }

    public List<StudentAnswer> getStudentAnswers() { return studentAnswers; }
    public void setStudentAnswers(List<StudentAnswer> answers) { this.studentAnswers = answers; }

    /**
     * حساب متوسط الدرجات لكل الطلاب
     * @return متوسط من 0 إلى 100
     */
    public double getAverageScore() {
        if(studentAnswers == null || studentAnswers.isEmpty()) return 0;
        double total = 0;
        for(StudentAnswer sa : studentAnswers){
            total += sa.getScore();
        }
        return total / studentAnswers.size();
    }
    public int calculateScoreForStudent(Student student) {
        int total = 0;
        for(StudentAnswer sa : studentAnswers){
            if(sa.getStudentId() == student.getId()) total += sa.getScore();
        }
        return total / questions.size();
    }

}

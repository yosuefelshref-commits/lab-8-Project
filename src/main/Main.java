package main;

import models.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        // --- Create users ---
        Instructor instructor = new Instructor(1, "Alice", "alice@mail.com", "pass123");
        Student student = new Student(2, "Bob", "bob@mail.com", "pass123");
        Admin admin = new Admin(3, "Charlie", "charlie@mail.com", "pass123");

        // --- Instructor creates a course ---
        Course course = new Course("Java Basics", "Learn Java from scratch", instructor.getUserId());

        // --- Instructor adds lessons ---
        Lesson lesson1 = new Lesson("Variables", "Learn variables in Java", null, course);
        Lesson lesson2 = new Lesson("Loops", "Learn loops in Java", null, course);
        course.addLesson(lesson1);
        course.addLesson(lesson2);

        // --- Instructor adds a quiz to lesson1 ---
        Quiz quiz1 = new Quiz(2); // passing score = 2
        ArrayList<String> optionsQ1 = new ArrayList<>();
        optionsQ1.add("int"); optionsQ1.add("float"); optionsQ1.add("String");
        Question q1 = new Question("Which data type is used for integers?", 1, optionsQ1, "int");

        ArrayList<String> optionsQ2 = new ArrayList<>();
        optionsQ2.add("for"); optionsQ2.add("if"); optionsQ2.add("switch");
        Question q2 = new Question("Which loop is counted?", 1, optionsQ2, "for");

        quiz1.addQuestion(q1);
        quiz1.addQuestion(q2);

        lesson1.setQuiz(quiz1);

        // --- Admin approves the course ---
        admin.approveCourse(course);

        // --- Student enrolls in the course ---
        student.enrollInCourse(course);

        // --- Student takes the quiz ---
        ArrayList<String> studentAnswers = new ArrayList<>();
        studentAnswers.add("int");   // correct
        studentAnswers.add("for");   // correct
        int score = lesson1.getQuiz().evaluateAnswers(studentAnswers);
        System.out.println("Student score: " + score + "/" + quiz1.getQuestions().size());

        if (lesson1.getQuiz().isPassed(score)) {
            System.out.println("Quiz passed!");
            student.completeLesson(course.getCourseId(), lesson1);
        } else {
            System.out.println("Quiz failed!");
        }
    }
}

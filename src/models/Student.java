package models;

import java.util.ArrayList;
import java.util.HashMap;

public class Student extends User {

    private ArrayList<Integer> enrolledCourseIds;
    private ArrayList<Certificate> certificates;

    private HashMap<Integer, HashMap<Integer, Boolean>> lessonCompletion;

    private HashMap<Integer, HashMap<Integer, Integer>> quizResults;

    public Student(int userId, String userName, String email, String password) {
        super(userId, userName, email, password);

        this.enrolledCourseIds = new ArrayList<>();
        this.certificates = new ArrayList<>();
        this.lessonCompletion = new HashMap<>();
        this.quizResults = new HashMap<>();
    }

    @Override
    public String getRole() {
        return "STUDENT";
    }

    public void enrollInCourse(Course course) {
        int courseId = course.getCourseId();

        if (!enrolledCourseIds.contains(courseId)) {
            enrolledCourseIds.add(courseId);
            lessonCompletion.put(courseId, new HashMap<>());
            quizResults.put(courseId, new HashMap<>());

            System.out.println("Enrolled Successfully in: " + course.getTitle());
        } else {
            System.out.println("Already enrolled in this course.");
        }
    }


    public void completeLesson(int courseId, Lesson lesson) {
        lessonCompletion.get(courseId).put(lesson.getLessonId(), true);
        System.out.println(getUserName() + " completed lesson: " + lesson.getTitle());
    }

    public boolean isLessonCompleted(int courseId, int lessonId) {
        return lessonCompletion.get(courseId).getOrDefault(lessonId, false);
    }

    public void takeQuiz(int courseId, Lesson lesson, ArrayList<Integer> answers) {

        Quiz quiz = lesson.getQuiz();
        if (quiz == null) {
            System.out.println("No quiz available for this lesson.");
            return;
        }

        // Convert Integer answers to Strings
        ArrayList<String> stringAnswers = new ArrayList<>();
        for (Integer ans : answers) {
            stringAnswers.add(ans.toString());
        }

        int score = quiz.evaluateAnswers(stringAnswers);
        quizResults.get(courseId).put(lesson.getLessonId(), score);

        System.out.println("Score = " + score + "/" + quiz.getMaxScore());

        if (quiz.isPassed(score)) { // check passing using the quiz method
            System.out.println("Quiz passed!");
            completeLesson(courseId, lesson);
        } else {
            System.out.println("Quiz failed.");
        }

        if (hasCompletedCourse(courseId, lesson.getCourse())) {
            earnCertificate(lesson.getCourse());
        }
    }


    public boolean hasCompletedCourse(int courseId, Course course) {

        for (Lesson lesson : course.getLessons()) {
            if (!isLessonCompleted(courseId, lesson.getLessonId())) {
                return false;
            }
        }
        return true;
    }

    public void earnCertificate(Course course) {
        Certificate cert = new Certificate(this, course);
        certificates.add(cert);

        System.out.println("🎉 Certificate Earned! → " + course.getTitle());
    }



    public ArrayList<Integer> getEnrolledCourseIds() {
        return enrolledCourseIds;
    }

    public ArrayList<Certificate> getCertificates() {
        return certificates;
    }

    public HashMap<Integer, HashMap<Integer, Integer>> getQuizResults() {
        return quizResults;
    }
}

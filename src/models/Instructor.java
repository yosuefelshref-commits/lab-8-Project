package models;

import java.util.ArrayList;

public class Instructor extends User {

    private ArrayList<Integer> authoredCourseIds;

    public Instructor(int userId, String userName, String email, String password) {
        super(userId, userName, email, password);
        this.authoredCourseIds = new ArrayList<>();
    }

    @Override
    public String getRole() {
        return "INSTRUCTOR";
    }

    public ArrayList<Integer> getAuthoredCourseIds() {
        return authoredCourseIds;
    }

    public Course createCourse(String title, String description) {

        for (int id : authoredCourseIds) {
            Course c = Course.getCourseById(id);
            if (c != null && c.getTitle().equalsIgnoreCase(title)) {
                System.out.println("This course already exists.");
                return null;
            }
        }

        Course course = new Course(title, description, this.getUserId()); // only 3 args
        authoredCourseIds.add(course.getCourseId());

        System.out.println("Course created: " + title);
        return course;
    }


    public void uploadLesson(Course course, Lesson lesson) {

        if (!authoredCourseIds.contains(course.getCourseId())) {
            System.out.println("You can't add a lesson to a course you didn't create.");
            return;
        }

        // lesson.setCourseId(course.getCourseId()); <-- REMOVE this line
        lesson.setCourse(course); // make sure lesson points to the course
        course.addLesson(lesson);

        System.out.println("Lesson added: " + lesson.getTitle());
    }


    public void addQuizToLesson(Course course, Lesson lesson, Quiz quiz) {

        if (!authoredCourseIds.contains(course.getCourseId())) {
            System.out.println("You can't add a quiz to a course you didn't create.");
            return;
        }

        if (!course.getLessons().contains(lesson)) {
            System.out.println("Lesson does not belong to this course.");
            return;
        }

        lesson.setQuiz(quiz);

        System.out.println("Quiz added to lesson: " + lesson.getTitle());
    }
}

package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Analytics {
    private Map<Course, Integer> quizScores;
    private List<Course> completedCourses;
    private Map<Course, Integer> lessonsCompleted;

    public Analytics() {
        quizScores = new HashMap<>();
        completedCourses = new ArrayList<>();
        lessonsCompleted = new HashMap<>();
    }

    public void updateQuizScore(Course course, int score) {
        if(course == null) throw new IllegalArgumentException("Course cannot be null");
        quizScores.put(course, score);
    }

    public void recordLessonCompletion(Lesson lesson) {
        if(lesson == null) throw new IllegalArgumentException("Lesson cannot be null");
        Course course = lesson.getCourse();
        lessonsCompleted.put(course, lessonsCompleted.getOrDefault(course, 0) + 1);
    }

    public void markCourseCompleted(Course course) {
        if(course == null) throw new IllegalArgumentException("Course cannot be null");
        if(!completedCourses.contains(course)) {
            completedCourses.add(course);
        }
    }

    public Map<Course, Integer> getQuizScores() {
        return new HashMap<>(quizScores);
    }

    public Map<Course, Integer> getLessonsCompleted() {
        return new HashMap<>(lessonsCompleted);
    }

    public List<Course> getCompletedCourses() {
        return new ArrayList<>(completedCourses);
    }

    public double getLessonCompletionPercentage(Course course) {
        int totalLessons = course.getLessons().size();
        return (lessonsCompleted.getOrDefault(course, 0) / (double) totalLessons) * 100;
    }
}

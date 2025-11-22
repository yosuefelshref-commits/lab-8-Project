package models;

public class Lesson {
    private static int nextId = 1; // make it static
    private int lessonId;
    private String title;
    private String content;
    private String resourceLink;
    private Course course;

    private Quiz quiz;

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }


    public Lesson(String title, String content, String resourceLink, Course course) {
        if(title == null || content == null) {
            throw new IllegalArgumentException("Title and content cannot be null");
        }
        this.lessonId = nextId++;
        this.title = title;
        this.content = content;
        this.resourceLink = resourceLink;
        this.course = course;
    }

    public void setTitle(String title) { this.title = title; }
    public void setContent(String content) { this.content = content; }
    public void setResourceLink(String resourceLink) { this.resourceLink = resourceLink; }
    public void setCourse(Course course) { this.course = course; }

    public String getTitle() { return title; }
    public int getLessonId() { return lessonId; }
    public String getContent() { return content; }
    public String getResourceLink() { return resourceLink; }
    public Course getCourse() { return course; }

    public void displayContent() {
        System.out.println("Lesson: " + title);
        System.out.println(content);
        if(resourceLink != null)
            System.out.println("Resource: " + resourceLink);
    }

    @Override
    public String toString() {
        return "Lesson ID: " + lessonId + ", Title: " + title;
    }
}

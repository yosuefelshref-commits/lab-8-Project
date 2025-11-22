package models;

public class Certificate {
    private static int nextId = 1;
    private final int certificateId;
    private final Student student;
    private final Course course;

    public Certificate(Student student, Course course) {
        if(student == null || course == null) {
            throw new IllegalArgumentException("Student and Course cannot be null");
        }
        this.certificateId = nextId++;
        this.student = student;
        this.course = course;
    }

    public Student getStudent() { return student; }
    public Course getCourse() { return course; }
    public int getCertificateId() { return certificateId; }

    @Override
    public String toString() {
        return "Certificate ID: " + certificateId +
                " | Student: " + student.getUserName() +
                " | Course: " + course.getTitle();
    }

}

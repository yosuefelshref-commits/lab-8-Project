package models;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Course {

    private static int nextId = 1;
    private static ArrayList<Course> allCourses = new ArrayList<>(); // <-- add this

    private int courseId;
    private String title;
    private String description;
    private int instructorId;   // avoid circular references
    private ArrayList<Lesson> lessons;
    private CourseStatus status;          // PENDING / APPROVED / REJECTED
    private Integer approvalAdminId;      // who approved/rejected
    private String lastAuditNote;         // log message
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Course(String title, String description, int instructorId) {
        this.courseId = nextId++;
        this.title = title;
        this.description = description;
        this.instructorId = instructorId;
        this.lessons = new ArrayList<>();
        this.status = CourseStatus.PENDING;
        this.approvalAdminId = null;
        this.lastAuditNote = "Created and pending approval.";
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();

        allCourses.add(this); // <-- add course to the static list
    }

    public int getCourseId() { return courseId; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public int getInstructorId() { return instructorId; }
    public ArrayList<Lesson> getLessons() { return lessons; }
    public CourseStatus getStatus() { return status; }
    public String getLastAuditNote() { return lastAuditNote; }

    public static Course getCourseById(int id) {
        for (Course c : allCourses) {
            if (c.getCourseId() == id) return c;
        }
        return null;
    }

    public void setTitle(String title) { this.title = title; updateTime(); }
    public void setDescription(String description) { this.description = description; updateTime(); }
    private void updateTime() { this.updatedAt = LocalDateTime.now(); }

    public void addLesson(Lesson lesson) {
        if (!lessons.contains(lesson)) {
            lessons.add(lesson);
            System.out.println("Lesson Added Successfully.");
        } else {
            System.out.println("This Lesson Already Exists.");
        }
        updateTime();
    }

    public void approve(int adminId) {
        this.status = CourseStatus.APPROVED;
        this.approvalAdminId = adminId;
        this.lastAuditNote = "Approved by admin #" + adminId;
        updateTime();
    }

    public void reject(int adminId, String reason) {
        this.status = CourseStatus.REJECTED;
        this.approvalAdminId = adminId;
        this.lastAuditNote = "Rejected by admin #" + adminId + ": " + reason;
        updateTime();
    }

    public boolean isVisibleToStudents() {
        return this.status == CourseStatus.APPROVED;
    }

    // Optional: get all courses
    public static ArrayList<Course> getAllCourses() {
        return new ArrayList<>(allCourses);
    }
}

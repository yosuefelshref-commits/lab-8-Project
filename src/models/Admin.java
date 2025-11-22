package models;
import java.util.List;

public class Admin extends User {

    public Admin(int userId, String userName, String email, String password) {
        super(userId, userName, email, password);
    }

    public String getRole(){
        return "Admin";
    }

    public void approveCourse(Course course) {
        if (course.getStatus() == CourseStatus.PENDING) {
            course.approve(this.getUserId());
            System.out.println("Course '" + course.getTitle() + "' approved by admin.");
        } else {
            System.out.println("Course is not pending approval.");
        }
    }

    public void rejectCourse(Course course, String reason) {
        if (course.getStatus() == CourseStatus.PENDING) {
            course.reject(this.getUserId(), reason);
            System.out.println("Course '" + course.getTitle() + "' rejected by admin.");
        } else {
            System.out.println("Course is not pending approval.");
        }
    }

    public void removeCourse(List<Course> allCourses, Course course) {
        if (allCourses.contains(course)) {
            allCourses.remove(course);
            System.out.println("Course " + course.getTitle() + " removed by admin.");
        } else {
            System.out.println("Course not found.");
        }
    }

    public void manageUser(List<User> allUsers, User user, String action) {
        switch (action.toLowerCase()) {
            case "delete":
                allUsers.remove(user);
                System.out.println("User " + user.getUserName() + " deleted by admin.");
                break;
            case "promote":
                if (user instanceof Student) {
                    Student student = (Student) user;
                    Instructor newInstructor = new Instructor(
                            student.getUserId(),
                            student.getUserName(),
                            student.getEmail(),
                            student.getPassword()
                    );
                    allUsers.remove(user);
                    allUsers.add(newInstructor);
                    System.out.println("Student " + student.getUserName() + " promoted to Instructor by admin.");
                } else {
                    System.out.println("Only students can be promoted to instructors.");
                }
                break;
            default:
                System.out.println("Action not recognized.");
        }
    }
}

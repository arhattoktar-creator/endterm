package kz.astana.endterm.dto;

public class EnrollmentRequest {
    private Integer studentId;
    private Integer courseId;

    public EnrollmentRequest() {}

    public Integer getStudentId() {
        return studentId;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }
}

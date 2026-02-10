package kz.astana.endterm.dto;

public class CourseRequest {
    private String name;
    private Integer credits;

    public CourseRequest() {
    }

    public CourseRequest(String name, Integer credits) {
        this.name = name;
        this.credits = credits;
    }

    public String getName() {
        return name;
    }

    public Integer getCredits() {
        return credits;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }
}

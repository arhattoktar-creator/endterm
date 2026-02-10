package kz.astana.endterm.model;

public class Student {
    private Integer id;
    private String name;
    private String email;

    public Student() {}

    public Student(Integer id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public Integer getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }

    public void setId(Integer id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
}

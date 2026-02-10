package kz.astana.endterm.controller;

import kz.astana.endterm.dto.CourseRequest;
import kz.astana.endterm.model.Course;
import kz.astana.endterm.service.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CourseRestController {

    private final CourseService service;

    public CourseRestController(CourseService service) {
        this.service = service;
    }

    @GetMapping("/courses")
    public List<Course> all() {
        return service.getAll();
    }

    @PostMapping("/courses")
    @ResponseStatus(HttpStatus.CREATED)
    public Course create(@RequestBody CourseRequest req) {
        return service.create(req);
    }

    @DeleteMapping("/courses/{id}")
    public void delete(@PathVariable int id) {
        service.delete(id);
    }
}

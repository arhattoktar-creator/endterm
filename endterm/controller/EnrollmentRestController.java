package kz.astana.endterm.controller;

import kz.astana.endterm.dto.EnrollmentRequest;
import kz.astana.endterm.model.Enrollment;
import kz.astana.endterm.service.EnrollmentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enrollments")
public class EnrollmentRestController {

    private final EnrollmentService service;

    public EnrollmentRestController(EnrollmentService service) {
        this.service = service;
    }

    @GetMapping
    public List<Enrollment> all() {
        return service.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Enrollment create(@RequestBody EnrollmentRequest req) {
        return service.create(req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        service.delete(id);
    }
}

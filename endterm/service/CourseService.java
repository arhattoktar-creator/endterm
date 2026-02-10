package kz.astana.endterm.service;

import kz.astana.endterm.dto.CourseRequest;
import kz.astana.endterm.exception.DuplicateResourceException;
import kz.astana.endterm.exception.InvalidInputException;
import kz.astana.endterm.exception.ResourceNotFoundException;
import kz.astana.endterm.model.Course;
import kz.astana.endterm.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    private final CourseRepository repo;

    public CourseService(CourseRepository repo) {
        this.repo = repo;
    }

    public Course create(CourseRequest req) {

        if (req == null) {
            throw new InvalidInputException("Request is null");
        }

        String name = req.getName();
        Integer creditsObj = req.getCredits();

        if (name == null || name.trim().isEmpty()) {
            throw new InvalidInputException("Name is empty");
        }

        if (creditsObj == null) {
            throw new InvalidInputException("Credits is null");
        }

        int credits = creditsObj;

        if (credits <= 0) {
            throw new InvalidInputException("Credits must be > 0");
        }

        if (repo.existsByName(name)) {
            throw new DuplicateResourceException("Course already exists: " + name);
        }

        return repo.save(name.trim(), credits);
    }

    public List<Course> getAll() {
        return repo.findAll();
    }

    public void delete(int id) {

        if (id <= 0) {
            throw new InvalidInputException("Invalid id");
        }

        boolean ok = repo.deleteById(id);

        if (!ok) {
            throw new ResourceNotFoundException("Course not found: id=" + id);
        }
    }

    public boolean existsById(int id) {
        return repo.existsById(id);
    }
}

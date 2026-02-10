package kz.astana.endterm.service;

import kz.astana.endterm.dto.StudentRequest;
import kz.astana.endterm.exception.DuplicateResourceException;
import kz.astana.endterm.exception.InvalidInputException;
import kz.astana.endterm.exception.ResourceNotFoundException;
import kz.astana.endterm.model.Student;
import kz.astana.endterm.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository repo;

    public StudentService(StudentRepository repo) {
        this.repo = repo;
    }

    public Student create(StudentRequest req) {

        if (req == null) {
            throw new InvalidInputException("Request is null");
        }

        String name = req.getName();
        String email = req.getEmail();

        if (name == null || name.trim().isEmpty()) {
            throw new InvalidInputException("Name is empty");
        }

        if (email == null || email.trim().isEmpty()) {
            throw new InvalidInputException("Email is empty");
        }

        if (repo.existsByEmail(email)) {
            throw new DuplicateResourceException("Student with this email already exists: " + email);
        }

        return repo.save(name.trim(), email.trim());
    }

    public List<Student> getAll() {
        return repo.findAll();
    }

    public void delete(int id) {

        if (id <= 0) {
            throw new InvalidInputException("Invalid id");
        }

        boolean ok = repo.deleteById(id);

        if (!ok) {
            throw new ResourceNotFoundException("Student not found: id=" + id);
        }
    }

    public boolean existsById(int id) {
        return repo.existsById(id);
    }
}

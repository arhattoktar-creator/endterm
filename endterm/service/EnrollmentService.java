package kz.astana.endterm.service;

import kz.astana.endterm.dto.EnrollmentRequest;
import kz.astana.endterm.exception.DuplicateResourceException;
import kz.astana.endterm.exception.InvalidInputException;
import kz.astana.endterm.exception.ResourceNotFoundException;
import kz.astana.endterm.model.Enrollment;
import kz.astana.endterm.repository.CourseRepository;
import kz.astana.endterm.repository.EnrollmentRepository;
import kz.astana.endterm.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnrollmentService {

    private final EnrollmentRepository repo;
    private final StudentRepository studentRepo;
    private final CourseRepository courseRepo;

    public EnrollmentService(EnrollmentRepository repo,
                             StudentRepository studentRepo,
                             CourseRepository courseRepo) {
        this.repo = repo;
        this.studentRepo = studentRepo;
        this.courseRepo = courseRepo;
    }

    public Enrollment create(EnrollmentRequest req) {

        if (req == null) {
            throw new InvalidInputException("Request is null");
        }

        int studentId = req.getStudentId();
        int courseId = req.getCourseId();

        if (studentId <= 0) {
            throw new InvalidInputException("Invalid studentId");
        }

        if (courseId <= 0) {
            throw new InvalidInputException("Invalid courseId");
        }

        if (!studentRepo.existsById(studentId)) {
            throw new ResourceNotFoundException("Student not found: id=" + studentId);
        }

        if (!courseRepo.existsById(courseId)) {
            throw new ResourceNotFoundException("Course not found: id=" + courseId);
        }

        if (repo.existsByStudentAndCourse(studentId, courseId)) {
            throw new DuplicateResourceException("Enrollment already exists");
        }

        return repo.save(studentId, courseId);
    }

    public List<Enrollment> getAll() {
        return repo.findAll();
    }

    public void delete(int id) {

        if (id <= 0) {
            throw new InvalidInputException("Invalid id");
        }

        boolean ok = repo.deleteById(id);

        if (!ok) {
            throw new ResourceNotFoundException("Enrollment not found: id=" + id);
        }
    }
}

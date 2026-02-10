package kz.astana.endterm.repository;

import kz.astana.endterm.exception.DatabaseOperationException;
import kz.astana.endterm.model.Enrollment;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EnrollmentRepository {

    private final DataSource dataSource;

    public EnrollmentRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Enrollment> findAll() {
        String sql = "SELECT id, student_id, course_id FROM enrollments ORDER BY id";

        List<Enrollment> list = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Enrollment e = new Enrollment(
                        rs.getInt("id"),
                        rs.getInt("student_id"),
                        rs.getInt("course_id")
                );
                list.add(e);
            }

            return list;

        } catch (SQLException ex) {
            throw new DatabaseOperationException("Failed to fetch enrollments", ex);
        }
    }

    public Enrollment save(int studentId, int courseId) {
        String sql = "INSERT INTO enrollments(student_id, course_id) VALUES (?, ?) RETURNING id";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, studentId);
            ps.setInt(2, courseId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    return new Enrollment(id, studentId, courseId);
                }
            }

            throw new DatabaseOperationException("Failed to insert enrollment", null);

        } catch (SQLException ex) {
            throw new DatabaseOperationException("Failed to insert enrollment", ex);
        }
    }

    public boolean deleteById(int id) {
        String sql = "DELETE FROM enrollments WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            int affected = ps.executeUpdate();
            return affected > 0;

        } catch (SQLException ex) {
            throw new DatabaseOperationException("Failed to delete enrollment", ex);
        }
    }

    public boolean existsByStudentAndCourse(int studentId, int courseId) {
        String sql = "SELECT 1 FROM enrollments WHERE student_id = ? AND course_id = ? LIMIT 1";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, studentId);
            ps.setInt(2, courseId);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException ex) {
            throw new DatabaseOperationException("Failed to check enrollment", ex);
        }
    }
}

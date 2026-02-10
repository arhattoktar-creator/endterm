package kz.astana.endterm.repository;

import kz.astana.endterm.exception.DatabaseOperationException;
import kz.astana.endterm.model.Student;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class StudentRepository {

    private final DataSource dataSource;

    public StudentRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Student> findAll() {
        String sql = "SELECT id, name, email FROM students ORDER BY id";

        List<Student> list = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Student s = new Student(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email")
                );
                list.add(s);
            }

            return list;

        } catch (SQLException ex) {
            throw new DatabaseOperationException("Failed to fetch students", ex);
        }
    }

    public Student findById(int id) {
        String sql = "SELECT id, name, email FROM students WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Student(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("email")
                    );
                }
                return null;
            }

        } catch (SQLException ex) {
            throw new DatabaseOperationException("Failed to fetch student by id", ex);
        }
    }

    public Student save(String name, String email) {
        String sql = "INSERT INTO students(name, email) VALUES (?, ?) RETURNING id";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(2, email);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    return new Student(id, name, email);
                }
            }

            throw new DatabaseOperationException("Failed to insert student", null);

        } catch (SQLException ex) {
            throw new DatabaseOperationException("Failed to insert student", ex);
        }
    }

    public Student update(int id, String name, String email) {
        String sql = "UPDATE students SET name = ?, email = ? WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setInt(3, id);

            int affected = ps.executeUpdate();

            if (affected == 0) {
                return null;
            }

            return new Student(id, name, email);

        } catch (SQLException ex) {
            throw new DatabaseOperationException("Failed to update student", ex);
        }
    }

    public boolean deleteById(int id) {
        String sql = "DELETE FROM students WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            int affected = ps.executeUpdate();
            return affected > 0;

        } catch (SQLException ex) {
            throw new DatabaseOperationException("Failed to delete student", ex);
        }
    }

    public boolean existsByEmail(String email) {
        String sql = "SELECT 1 FROM students WHERE LOWER(email) = LOWER(?) LIMIT 1";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException ex) {
            throw new DatabaseOperationException("Failed to check student email", ex);
        }
    }

    public boolean existsById(int id) {
        String sql = "SELECT 1 FROM students WHERE id = ? LIMIT 1";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException ex) {
            throw new DatabaseOperationException("Failed to check student id", ex);
        }
    }
}

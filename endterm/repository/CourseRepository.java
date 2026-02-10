package kz.astana.endterm.repository;

import kz.astana.endterm.exception.DatabaseOperationException;
import kz.astana.endterm.model.Course;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CourseRepository {

    private final DataSource dataSource;

    public CourseRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Course> findAll() {
        String sql = "SELECT id, name, credits FROM courses ORDER BY id";

        List<Course> list = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Course c = new Course(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("credits")
                );
                list.add(c);
            }

            return list;

        } catch (SQLException ex) {
            throw new DatabaseOperationException("Failed to fetch courses", ex);
        }
    }

    public Course findById(int id) {
        String sql = "SELECT id, name, credits FROM courses WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Course(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getInt("credits")
                    );
                }
                return null;
            }

        } catch (SQLException ex) {
            throw new DatabaseOperationException("Failed to fetch course by id", ex);
        }
    }

    public Course save(String name, int credits) {
        String sql = "INSERT INTO courses(name, credits) VALUES (?, ?) RETURNING id";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setInt(2, credits);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    return new Course(id, name, credits);
                }
            }

            throw new DatabaseOperationException("Failed to insert course", null);

        } catch (SQLException ex) {
            throw new DatabaseOperationException("Failed to insert course", ex);
        }
    }

    public Course update(int id, String name, int credits) {
        String sql = "UPDATE courses SET name = ?, credits = ? WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setInt(2, credits);
            ps.setInt(3, id);

            int affected = ps.executeUpdate();

            if (affected == 0) {
                return null;
            }

            return new Course(id, name, credits);

        } catch (SQLException ex) {
            throw new DatabaseOperationException("Failed to update course", ex);
        }
    }

    public boolean deleteById(int id) {
        String sql = "DELETE FROM courses WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            int affected = ps.executeUpdate();
            return affected > 0;

        } catch (SQLException ex) {
            throw new DatabaseOperationException("Failed to delete course", ex);
        }
    }

    public boolean existsByName(String name) {
        String sql = "SELECT 1 FROM courses WHERE LOWER(name) = LOWER(?) LIMIT 1";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, name);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException ex) {
            throw new DatabaseOperationException("Failed to check course name", ex);
        }
    }

    public boolean existsById(int id) {
        String sql = "SELECT 1 FROM courses WHERE id = ? LIMIT 1";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException ex) {
            throw new DatabaseOperationException("Failed to check course id", ex);
        }
    }
}

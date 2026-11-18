package skillmatch.dao;

import skillmatch.exception.DatabaseException;
import skillmatch.util.DatabaseConnection;
import java.sql.*;
import java.util.*;

public class SkillDAO {

    public Set<String> findByStudent(int studentId) throws DatabaseException {
        String sql = "SELECT s.name FROM skill s JOIN student_skill ss "
                   + "ON s.skill_id = ss.skill_id WHERE ss.student_id = ?";
        return query(sql, studentId);
    }

    public Set<String> findByInternship(int internshipId) throws DatabaseException {
        String sql = "SELECT s.name FROM skill s JOIN internship_skill i "
                   + "ON s.skill_id = i.skill_id WHERE i.internship_id = ?";
        return query(sql, internshipId);
    }

    private Set<String> query(String sql, int id) throws DatabaseException {
        Set<String> out = new HashSet<>();
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) out.add(rs.getString(1));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Could not read skills.", e);
        }
        return out;
    }
}

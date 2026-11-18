package skillmatch.dao;

import skillmatch.exception.DatabaseException;
import skillmatch.model.Application;
import skillmatch.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ApplicationDAO {

    public boolean exists(int studentId, int internshipId) throws DatabaseException {
        String sql = "SELECT 1 FROM application WHERE student_id=? AND internship_id=?";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            ps.setInt(2, internshipId);
            try (ResultSet rs = ps.executeQuery()) { return rs.next(); }
        } catch (SQLException e) {
            throw new DatabaseException("Could not check application.", e);
        }
    }

    public void insert(int studentId, int internshipId, double score)
            throws DatabaseException {
        String sql = "INSERT INTO application(student_id,internship_id,match_score) "
                   + "VALUES (?,?,?)";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            ps.setInt(2, internshipId);
            ps.setDouble(3, score);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Could not save application.", e);
        }
    }

    public List<Application> findByStudent(int studentId) throws DatabaseException {
        String sql = "SELECT application_id,internship_id,status,match_score "
                   + "FROM application WHERE student_id = ? ORDER BY applied_on DESC";
        List<Application> list = new ArrayList<>();
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Application(rs.getInt(1), studentId, rs.getInt(2),
                            Application.Status.valueOf(rs.getString(3)), rs.getDouble(4)));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Could not load applications.", e);
        }
        return list;
    }

    public void updateStatus(int applicationId, Application.Status status)
            throws DatabaseException {
        String sql = "UPDATE application SET status = ? WHERE application_id = ?";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, status.name());
            ps.setInt(2, applicationId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Could not update status.", e);
        }
    }
}

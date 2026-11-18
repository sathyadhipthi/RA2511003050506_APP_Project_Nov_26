package skillmatch.dao;

import skillmatch.exception.DatabaseException;
import skillmatch.util.DatabaseConnection;
import java.sql.*;

public class CompanyDAO {

    public int insert(String name, String email, String hash, String salt)
            throws DatabaseException {
        String sql = "INSERT INTO company(name,email,password_hash,salt) VALUES (?,?,?,?)";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, hash);
            ps.setString(4, salt);
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                return keys.next() ? keys.getInt(1) : -1;
            }
        } catch (SQLException e) {
            throw new DatabaseException("Could not register company.", e);
        }
    }

    /** Administrator action: mark a company as verified. */
    public void verify(int companyId) throws DatabaseException {
        String sql = "UPDATE company SET verified = TRUE WHERE company_id = ?";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, companyId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Could not verify company.", e);
        }
    }
}

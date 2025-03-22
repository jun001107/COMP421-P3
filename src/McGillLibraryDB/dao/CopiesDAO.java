package McGillLibraryDB.dao;

import java.sql.*;

public class CopiesDAO {
    private Connection connection;

    public CopiesDAO(Connection connection) {
        this.connection = connection;
    }

    public int addCopies(int copy_id, String isbn, String library_name) {
        String query = "INSERT INTO copies VALUES (?, ?, 'available', ?)";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1,copy_id);
            ps.setString(2, isbn);
            ps.setString(3, library_name);

            return ps.executeUpdate();
        } catch (SQLException e) {
            int sqlCode = e.getErrorCode();
            System.out.println("<< " + sqlCode + ": " + e.getMessage() + " >>");
            return -1;
        }
    }

    public int deleteCopies(String isbn, int copy_id) {
        String query = "DELETE FROM copies WHERE isbn = ? AND copy_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, isbn);
            ps.setInt(2, copy_id);

            return ps.executeUpdate();
        } catch (SQLException e) {
            int sqlCode = e.getErrorCode();
            System.out.println("<< " + sqlCode + ": " + e.getMessage() + " >>");
            return -1;
        }
    }

    public int updateStatus(String isbn, int copy_id, String status) {
        String query = "UPDATE copies SET status = ? WHERE isbn = ? AND copy_id = ?";

        try(PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, status);
            ps.setString(2, isbn);
            ps.setInt(3, copy_id);
            return ps.executeUpdate();
        } catch (SQLException e) {
            int sqlCode = e.getErrorCode();
            System.out.println("<< " + sqlCode + ": " + e.getMessage() + " >>");
            return -1;
        }
    }
}

package McGillLibraryDB.dao;

import java.sql.*;

public class Copies {
    private Connection connection;

    public Copies(Connection connection) {
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
            String sqlStatement = e.getSQLState();
            System.out.println(sqlCode + " " + sqlStatement);
            System.out.println("Message: " + e.getMessage());
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
            String sqlStatement = e.getSQLState();
            System.out.println(sqlCode + " " + sqlStatement);
            System.out.println("Message: " + e.getMessage());
            return -1;
        }
    }
}

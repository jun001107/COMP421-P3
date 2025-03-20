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
            ps.executeUpdate();

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) return rowsAffected;
            else return 0;

        } catch (SQLException e) {
            int sqlCode = e.getErrorCode();
            String sqlStatement = e.getSQLState();
            System.out.println(sqlCode + " " + sqlStatement);
            System.out.println("Message: " + e.getMessage());
            return -1;
        }
    }
}

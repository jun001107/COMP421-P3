package McGillLibraryDB.dao;

import java.sql.*;

public class Authors {
    private final Connection connection;

    // Creating a table
    public Authors(Connection connection) {
        this.connection = connection;
    }

    // Inserting Data into the table
    public void addAuthor(int a_id, String a_name){
        String query = "INSERT INTO authors VALUES (?,?)";

        try(PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, a_id);
            ps.setString(2, a_name);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Author added successfully");
            } else {
                System.out.println("Insert failed; Author not added");
            }
        } catch (SQLException e) {
            System.out.println("Error inserting id and author: " + e.getMessage());
        }
    }

    // Querying a table
    public void getAuthor() {
        String query = "SELECT a_id, a_name FROM Authors;";

        try(Statement stmt = this.connection.createStatement();
            ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int a_id = rs.getInt(1);
                String name = rs.getString(2);
                System.out.println("id: " + a_id + ", name: " + name);
            }
            System.out.println("DONE");
        } catch (SQLException e) {
            int sqlCode = e.getErrorCode();
            String sqlState = e.getSQLState();

            System.out.println("Code: " + sqlCode + " sqlState: " + sqlState);
            System.out.println("Message: " + e.getMessage());
        }
    }

    // Updating a table
    public void updateAuthor(int a_id, String new_name){
        String query = "UPDATE Authors SET a_name = ? WHERE a_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, new_name);
            ps.setInt(2, a_id);
        } catch (SQLException e) {
            System.out.println("Error in updating id and author: " + e.getMessage());
        }
    }

    // Delete a Data from the table
    public void deleteAuthor(int a_id){
        String query = "DELETE FROM Authors WHERE a_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, a_id);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Author deleted successfully");
            } else {
                System.out.println("Delete failed; Author not deleted");
            }
        } catch (SQLException e) {
            System.out.println("Error in deleting id and author: " + e.getMessage());
        }
    }
}

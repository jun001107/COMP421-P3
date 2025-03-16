package McGillLibraryDB.dao;

import java.sql.*;

public class Authors {
    private Connection connection;

    // Creating a table
    public Authors(Connection connection) {
        this.connection = connection;
    }

    // Inserting Data into the table
    public void addAuthor(int a_id, String a_name){

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
    public void updateAuthor(){

    }

    // Delete a Data from the table
    public void deleteAuthor(){

    }
}

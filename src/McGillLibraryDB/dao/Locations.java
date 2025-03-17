package McGillLibraryDB.dao;

import java.sql.*;

public class Locations {
    private Connection connection;

    public Locations(Connection connection) {
        this.connection = connection;
    }

    public void addLocation(String lib_name, String address) {
        String query = "INSERT INTO locations VALUES (?,?)";

        try(PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, lib_name);
            ps.setString(2, address);
            int rowsAffected = ps.executeUpdate();
            if(rowsAffected > 0) {
                System.out.println("Successfully added location");
            } else {
                System.out.println("Failed to add location");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}

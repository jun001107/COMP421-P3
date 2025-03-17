package McGillLibraryDB.dao;

import java.sql.*;

public class Citizens {
    private Connection connection;

    public Citizens(Connection connection) {
        this.connection = connection;
    }

    public void addCitizen(int card_number, String c_name, String c_email, Date dob){
        String query = "insert into citizens values(?,?,?,?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, card_number);
            ps.setString(2, c_name);
            ps.setString(3, c_email);
            ps.setDate(4, dob);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Citizens added successfully");
            } else {
                System.out.println("Insert failed; Please try again");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void getCitizen(int card_number){
    }
}

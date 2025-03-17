package McGillLibraryDB.dao;

import java.sql.*;

public class Books {
    private final Connection connection;

    public Books(Connection connection) {
        this.connection = connection;
    }

    public void addBook(String isbn, String title, String genre, int pages){
        String query = "INSERT INTO books VALUES (?, ?, ?, ?)";

        try(PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, isbn);
            ps.setString(2, title);
            ps.setString(3, genre);
            ps.setInt(4, pages);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Book added successfully");
            } else {
                System.out.println("Book not added");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void getBook() {
        String query = "SELECT * FROM books";

        try(Statement stmt = this.connection.createStatement();
            ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String isbn = rs.getString("isbn");
                String title = rs.getString("title");
                String genre = rs.getString("genre");
                int pages = rs.getInt("pages");
                System.out.println(isbn + " " + title + " " + genre + " " + pages);
            }
        } catch (SQLException e) {
            int sqlCode = e.getErrorCode();
            String sqlStatement = e.getSQLState();
            System.out.println(sqlCode + " " + sqlStatement);
            System.out.println("Message: " + e.getMessage());
        }
    }
}

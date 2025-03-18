package McGillLibraryDB.dao;

import java.sql.*;

public class Books {
    private final Connection connection;

    public Books(Connection connection) {
        this.connection = connection;
    }

    public void getBookByGenre(String genre) {
        String query = "SELECT b.title, a.a_name FROM Books b " +
                "JOIN Wrote w ON b.isbn = w.isbn " +
                "JOIN Authors a ON w.a_id = a.a_id " +
                "WHERE b.genre LIKE ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, "%" + genre + "%");

            try (ResultSet rs = ps.executeQuery()) {
                boolean hasResults = false;
                String title;
                String author;

                while (rs.next()) {
                    hasResults = true;
                    title = rs.getString("title");
                    author = rs.getString("a_name");

                    System.out.println("<" + title + "> written by" + author);
                }

                if (!hasResults) {
                    System.out.println("No books found for genre " + genre);
                }
            }
        } catch (SQLException e) {
            int sqlCode = e.getErrorCode();
            String sqlStatement = e.getSQLState();
            System.out.println(sqlCode + " " + sqlStatement);
            System.out.println("Message: " + e.getMessage());
        }
    }

    public void getBookByAuthor(String author) {
        String query = "SELECT b.title, a.a_name FROM Books b " +
                "JOIN Wrote w ON b.isbn = w.isbn " +
                "JOIN Authors a ON w.a_id = a.a_id " +
                "WHERE a.a_name = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, author);

            try (ResultSet rs = ps.executeQuery()) {
                boolean hasResults = false;
                String title;

                while (rs.next()) {
                    hasResults = true;
                    title = rs.getString("title");

                    System.out.println("<" + title + "> written by " + author);
                }

                if (!hasResults) {
                    System.out.println("No books found written by " + author);
                }
            }
        } catch (SQLException e) {
            int sqlCode = e.getErrorCode();
            String sqlStatement = e.getSQLState();
            System.out.println(sqlCode + " " + sqlStatement);
            System.out.println("Message: " + e.getMessage());
        }
    }

    public void getBookByTitle(String title) {
        String query = "SELECT b.title, a.a_name " +
                        "FROM Books b " +
                        "JOIN Wrote w ON b.isbn = w.isbn " +
                        "JOIN Authors a ON w.a_id = a.a_id " +
                        "WHERE b.title = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, title);

            try (ResultSet rs = ps.executeQuery()) {
                boolean hasResults = false;
                String author;

                while (rs.next()) {
                    hasResults = true;
                    author = rs.getString("author");

                    System.out.println("<" + title + "> written by" + author);
                }

                if (!hasResults) {
                    System.out.println("No books found named " + title);
                }
            }
        } catch (SQLException e) {
            int sqlCode = e.getErrorCode();
            String sqlStatement = e.getSQLState();
            System.out.println(sqlCode + " " + sqlStatement);
            System.out.println("Message: " + e.getMessage());
        }
    }
}
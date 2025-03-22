package McGillLibraryDB.dao;

import java.sql.*;

public class BooksDAO {
    private final Connection connection;

    public BooksDAO(Connection connection) {
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

                String format = "%-40s | %-40s%n";
                System.out.printf(format, "Title", "Author");
                System.out.println("----------------------------------------------------------------");

                while (rs.next()) {
                    hasResults = true;
                    title = rs.getString("title");
                    author = rs.getString("a_name");

                    System.out.printf(format, title, author);
                }

                if (!hasResults) {
                    System.out.println("==> No books found for genre " + genre);
                }
            }
        } catch (SQLException e) {
            int sqlCode = e.getErrorCode();
            System.out.println("<< " + sqlCode + ": " + e.getMessage() + " >>");
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

                String format = "%-40s | %-40s%n";
                System.out.printf(format, "Title", "Author");
                System.out.println("----------------------------------------------------------------");

                while (rs.next()) {
                    hasResults = true;
                    title = rs.getString("title");

                    System.out.printf(format, title, author);
                }

                if (!hasResults) {
                    System.out.println("==> No books found written by " + author);
                }
            }
        } catch (SQLException e) {
            int sqlCode = e.getErrorCode();
            System.out.println("<< " + sqlCode + ": " + e.getMessage() + " >>");
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

                String format = "%-40s | %-40s%n";
                System.out.printf(format, "Title", "Author");
                System.out.println("----------------------------------------------------------------");

                while (rs.next()) {
                    hasResults = true;
                    author = rs.getString("author");

                    System.out.printf(format, author, title);
                }

                if (!hasResults) {
                    System.out.println("==> No books found named " + title);
                }
            }
        } catch (SQLException e) {
            int sqlCode = e.getErrorCode();
            System.out.println("<< " + sqlCode + ": " + e.getMessage() + " >>");
        }
    }

    public void getBookAvailability() {
        String query = "WITH grouped_books AS ( " +
                "    SELECT b.isbn, c.lib_name, COUNT(b.isbn) AS number_of_copies " +
                "    FROM Books b " +
                "    JOIN Copies c ON b.isbn = c.isbn " +
                "    WHERE c.status = 'available' " +
                "    GROUP BY b.isbn, c.lib_name " +
                "), " +
                "author_list AS ( " +
                "    SELECT w.isbn, " +
                "           LISTAGG(a.a_name, ', ') WITHIN GROUP ( ORDER BY a.a_name ) AS authors " +
                "    FROM Wrote w " +
                "    JOIN Authors a ON w.a_id = a.a_id " +
                "    GROUP BY w.isbn " +
                ") " +
                "SELECT " +
                "    b.isbn, " +
                "    b.title, " +
                "    al.authors, " +
                "    g.lib_name, " +
                "    g.number_of_copies " +
                "FROM Books b " +
                "JOIN grouped_books g ON b.isbn = g.isbn " +
                "JOIN author_list al ON b.isbn = al.isbn";

        try (Statement statement = this.connection.createStatement();
            ResultSet rs = statement.executeQuery( query )) {
            String format = "%-15s | %-40s | %-40s | %-30s | %-10s%n";
            System.out.printf(format, "ISBN", "Title", "Authors", "Library", "Number of copies");
            System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------");
            boolean hasResults = false;

            while (rs.next()) {
                hasResults = true;
                String isbn = rs.getString(1);
                String title = rs.getString(2);
                String author = rs.getString(3);
                String libName = rs.getString(4);
                int numberOfCopies = rs.getInt(5);
                System.out.printf(format, isbn, title, author, libName, numberOfCopies);
            }

            if (!hasResults) {
                System.out.println("==> Currently all books are reserved.");
            }
        } catch (SQLException e) {
            int sqlCode = e.getErrorCode();
            System.out.println("<< " + sqlCode + ": " + e.getMessage() + " >>");
        }
    }
}
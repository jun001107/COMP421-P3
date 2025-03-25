package dao;

import java.sql.*;

public class CopiesDAO {
    private Connection connection;

    public CopiesDAO(Connection connection) {
        this.connection = connection;
    }

    private int newCopyID(String isbn) {
        String query = "SELECT MAX(copy_id) FROM COPIES WHERE ISBN = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, isbn);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int maxLoanId = rs.getInt(1);
                    return rs.wasNull() ? 1 : maxLoanId + 1;
                }
                return -1;
            }
        } catch (SQLException e) {
            int sqlCode = e.getErrorCode();
            System.out.println("<< " + sqlCode + ": " + e.getMessage() + " >>");
            return -1;
        }
    }

    private int addBookToLib(String isbn, String lib_name) {
        String query = "INSERT INTO HAS_BOOK (ISBN, LIB_NAME) VALUES(?, ?)";

        try(PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, isbn);
            ps.setString(2, lib_name);
            return ps.executeUpdate();
        } catch(SQLException e) {
            int sqlCode = e.getErrorCode();
            System.out.println("<< " + sqlCode + ": " + e.getMessage() + " >>");
            return -1;
        }
    }

    private int checkHasBook(String isbn, String lib_name) {
        String query = "SELECT * FROM HAS_BOOK WHERE ISBN = ? AND lib_name = ?";

        try(PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, isbn);
            ps.setString(2, lib_name);
            try(ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return 1;
                else return addBookToLib(isbn, lib_name);
            }
        } catch(SQLException e) {
            int sqlCode = e.getErrorCode();
            System.out.println("<< " + sqlCode + ": " + e.getMessage() + " >>");
            return -1;
        }
    }

    public int addCopies(String isbn, String library_name) {
        String query = "INSERT INTO copies VALUES (?, ?, 'available', ?)";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            checkHasBook(isbn, library_name);
            ps.setInt(1, newCopyID(isbn));
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

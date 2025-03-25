package dao;

import java.sql.*;

public class LoansDAO {
    private final Connection connection;

    public LoansDAO(Connection connection) {
        this.connection = connection;
    }

    private int getNewLoanID(int copy_id, String isbn) {
        String query = "SELECT MAX(loan_id) FROM loans WHERE isbn=? AND copy_id=?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, isbn);
            ps.setInt(2, copy_id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int maxLoanId = rs.getInt(1);
                    return rs.wasNull() ? 1 : maxLoanId + 1;
                }
                return -1;
            }
        } catch(SQLException e) {
            int sqlCode = e.getErrorCode();
            String sqlStatement = e.getSQLState();
            System.out.println("<< " + sqlCode + ": " + sqlStatement + " >>");
            return -1;
        }
    }

    private CopiesDAO initializeCopies() {
        return new CopiesDAO(connection);
    }

    public int addLoans(int card_num, String isbn, int copy_id) {
        int newLoanID = getNewLoanID(copy_id, isbn);
        Date today = new Date(System.currentTimeMillis());
        Date endDate = new Date(today.getTime() + 7 * 86400000L);
        int status;

        if (!isBookAvailable(copy_id, isbn)) return 0;

        String query = "INSERT INTO Loans VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, newLoanID);
            ps.setInt(2, copy_id);
            ps.setString(3, isbn);
            ps.setDate(4, today);
            ps.setDate(5, endDate);
            ps.setInt(6, card_num);

            status = ps.executeUpdate();
            if (status > 0) {
                initializeCopies().updateStatus(isbn, copy_id, "unavailable");
            }

            return status;

        } catch (SQLException e) {
            int sqlCode = e.getErrorCode();
            String sqlStatement = e.getSQLState();
            System.out.println("<< " + sqlCode + ": " + sqlStatement + " >>");
            return -1;
        }
    }

    private boolean isBookAvailable(int copy_id, String isbn) {
        String query = "SELECT status FROM copies WHERE isbn=? AND copy_id=?";

        try(PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, isbn);
            ps.setInt(2, copy_id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return (rs.getString("status").equals("available"));
                }
            }
            return false;
        } catch (SQLException e) {
            int sqlCode = e.getErrorCode();
            String sqlStatement = e.getSQLState();
            System.out.println("<< " + sqlCode + ": " + sqlStatement + " >>");
            return false;
        }
    }

    private void callUpdateStatusFines() {
        String sql = "{CALL UpdateResFines()}";

        try (CallableStatement cstmt = connection.prepareCall(sql)) {
            cstmt.execute();
        } catch (SQLException e) {
            int sqlCode = e.getErrorCode();
            System.out.println("<< " + sqlCode + ": " + e.getMessage() + " >>");
        }
    }

    public int updateCopyStatus(String isbn, int copy_id) {
        int status = initializeCopies().updateStatus(isbn, copy_id, "available");

        if (status > 0) { // Update end_date = current date
            String query = "UPDATE Loans SET end_date = CURRENT DATE " +
                    "WHERE isbn = ? AND copy_id = ? " +
                    "AND loan_id = ( " +
                    "   SELECT MAX(loan_id) "+
                    "   FROM Loans WHERE isbn = ? AND copy_id = ?)";

            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setString(1, isbn);
                ps.setInt(2, copy_id);
                ps.setString(3, isbn);
                ps.setInt(4, copy_id);
                ps.executeUpdate();
            } catch(SQLException e) {
                System.out.println("<< " + e.getErrorCode() + ": " + e.getSQLState() + " >>");
                return -1;
            }
            callUpdateStatusFines();
        }

        return status;
    }

    public void getAllLoans() {
        String query = "SELECT l.loan_id, l.copy_id, l.isbn, l.start_date, l.end_date, l.card_number, " +
                "CASE WHEN l.loan_id = (SELECT MAX(loan_id) FROM Loans WHERE isbn = l.isbn AND copy_id = l.copy_id) " +
                "THEN c.status ELSE 'available' END as effective_status " +
                "FROM Loans l " +
                "JOIN Copies c ON l.isbn = c.isbn AND l.copy_id = c.copy_id";

        try (Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(query)) {
            boolean hasResult = false;
            String format = "%-10s | %-10s | %-15s | %-15s | %-15s | %-12s | %-15s%n";
            System.out.printf(format, "Loan ID", "Copy ID", "ISBN", "START DATE", "END DATE", "Card Number", "Status");
            System.out.println("---------------------------------------------------------------------------------------------------------");
            while (rs.next()) {
                hasResult = true;
                int loan_id = rs.getInt(1);
                int copy_id = rs.getInt(2);
                String isbn = rs.getString(3);
                Date start_date = rs.getDate(4);
                Date end_date = rs.getDate(5);
                int card_number = rs.getInt(6);
                String status = (rs.getString("effective_status").equals("available"))? "returned":"Not returned";

                System.out.printf(format, loan_id, copy_id, isbn, start_date, end_date, card_number, status);
            }

            if (!hasResult) {
                System.out.println("==> History is empty");
            }
        } catch (SQLException e) {
            int sqlCode = e.getErrorCode();
            String sqlStatement = e.getSQLState();
            System.out.println("<< " + sqlCode + ": " + sqlStatement + " >>");
        }
    }
}

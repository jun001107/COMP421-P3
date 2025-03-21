package McGillLibraryDB.dao;

import java.sql.*;

public class Loans {
    private final Connection connection;

    public Loans(Connection connection) {
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
            return -1;
        }
    }

    public int addLoans(int card_num, String isbn, int copy_id) {
        int newLoanID = getNewLoanID(copy_id, isbn);
        Date today = new Date(System.currentTimeMillis());
        Date endDate = new Date(today.getTime() + 7 * 86400000L);
        String comment;

        if (!isBookAvailable(copy_id, isbn)) return 0;

        String query = "INSERT INTO Loans VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, newLoanID);
            ps.setInt(2, copy_id);
            ps.setString(3, isbn);
            ps.setDate(4, today);
            ps.setDate(5, endDate);
            ps.setInt(6, card_num);

            return ps.executeUpdate();
        } catch (SQLException e) {
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
            return false;
        }
    }

    public int updateCopyStatus(String isbn, int copy_id) {
        String query = "UPDATE copies SET status=? WHERE isbn=? AND copy_id=?";

        try(PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, "available");
            ps.setString(2, isbn);
            ps.setInt(3, copy_id);
            return ps.executeUpdate();

        } catch (SQLException e) {
            int sqlCode = e.getErrorCode();
            String sqlStatement = e.getSQLState();
            System.out.println(sqlCode + " " + sqlStatement);
            System.out.println("Message: " + e.getMessage());
            return -1;
        }
    }

    public void getAllLoans() {
        String query = "SELECT loan_id, copy_id, isbn, start_date, end_date, card_number " +
                "FROM Loans";

        try (Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(query)) {
            boolean hasResult = false;
            String format = "%-15s | %-15s | %-15s | %-15s | %-15s | %-15s%n";
            System.out.printf(format, "Loan ID", "Copy ID", "ISBN", "START DATE", "END DATE", "Card Number");
            System.out.println("-----------------------------------------------------------------------------------------------------------");
            while (rs.next()) {
                hasResult = true;
                int loan_id = rs.getInt("loan_id");
                int copy_id = rs.getInt("copy_id");
                String isbn = rs.getString("isbn");
                Date start_date = rs.getDate("start_date");
                Date end_date = rs.getDate("end_date");
                int card_number = rs.getInt("card_number");

                System.out.printf(format, loan_id, copy_id, isbn, start_date, end_date, card_number);
            }

            if (!hasResult) {
                System.out.println("History is empty");
            }
        } catch (SQLException e) {
            int sqlCode = e.getErrorCode();
            String sqlStatement = e.getSQLState();
            System.out.println(sqlCode + " " + sqlStatement);
            System.out.println("Message: " + e.getMessage());
        }
    }
}

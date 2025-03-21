package McGillLibraryDB.dao;

import java.sql.*;

public class ReportDAO {
    private final Connection connection;

    public ReportDAO(Connection connection) {
        this.connection = connection;
    }

    public void getPopularBooks() {
        String query = "WITH loan_counts AS ( " +
                "    SELECT l.isbn, COUNT(l.loan_id) AS total_loans " +
                "    FROM Loans l " +
                "    GROUP BY l.isbn " +
                "), " +
                "author_list AS ( " +
                "    SELECT w.isbn, LISTAGG(a.a_name, ', ') WITHIN GROUP (ORDER BY a.a_name) AS authors " +
                "    FROM Wrote w " +
                "    JOIN Authors a ON a.a_id = w.a_id " +
                "    GROUP BY w.isbn " +
                ") " +
                "SELECT b.isbn, b.title, al.authors, lc.total_loans " +
                "FROM Books b " +
                "JOIN loan_counts lc ON b.isbn = lc.isbn " +
                "JOIN author_list al ON b.isbn = al.isbn " +
                "ORDER BY lc.total_loans DESC";

        try (PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            int total_loans;
            String isbn, title,authors;

            String format = "%-15s | %-40s | %-30s | %-10s%n";
            System.out.printf(format, "ISBN", "Title", "Authors", "Total Loans");
            System.out.println("---------------------------------------------------------------------------------------------------------------------------------------");
            boolean hasResults = false;

            while (rs.next()) {
                hasResults = true;
                isbn = rs.getString(1);
                title = rs.getString(2);
                authors = rs.getString(3);
                total_loans = rs.getInt(4);

                System.out.printf(format, isbn, title, authors, total_loans);
            }
        } catch (SQLException e) {
            int sqlCode = e.getErrorCode();
            String sqlStatement = e.getSQLState();
            System.out.println(sqlCode + " " + sqlStatement);
            System.out.println("Message: " + e.getMessage());
        }
    }

    public void getLibraryPerformance() {
        String query = "SELECT " +
                "    L.lib_name, " +
                "    COUNT(DISTINCT Cp.copy_id) AS total_copies, " +
                "    COUNT(DISTINCT CASE WHEN Cp.status = 'unavailable' THEN Cp.copy_id END) AS active_loans, " +
                "    COUNT(DISTINCT Fn.loan_id) AS overdue_loans, " +
                "    COALESCE(SUM(CASE WHEN Fn.paid = 'N' THEN Fn.amount ELSE 0 END), 0) AS " +
                "                                  total_unpaid_fines " +
                "FROM Locations L " +
                "LEFT JOIN Copies Cp ON L.lib_name = Cp.lib_name " +
                "LEFT JOIN Loans Ln ON Cp.copy_id = Ln.copy_id AND Cp.isbn = Ln.isbn " +
                "LEFT JOIN Fines Fn ON Ln.loan_id = Fn.loan_id AND Ln.copy_id = Fn.copy_id AND Ln.isbn = Fn.isbn " +
                "GROUP BY L.lib_name;";

        try (PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            String lib_name;
            int total_copies, active_loans, overdue_loans, total_unpaid_fines;
            String format = "%-40s | %-15s | %-15s | %-15s | %-15s%n";
            System.out.printf(format, "Library", "Total Copies", "Active Loans", "Overdue Loans", "Total Unpaid Fines");
            System.out.println("-----------------------------------------------------------------------------------------------------------------");

            while (rs.next()) {
                lib_name = rs.getString(1);
                total_copies = rs.getInt(2);
                active_loans = rs.getInt(3);
                overdue_loans = rs.getInt(4);
                total_unpaid_fines = rs.getInt(5);

                System.out.printf(format, lib_name, total_copies, active_loans, overdue_loans, total_unpaid_fines);
                }

        } catch(SQLException e) {
            int sqlCode = e.getErrorCode();
            String sqlStatement = e.getSQLState();
            System.out.println(sqlCode + " " + sqlStatement);
            System.out.println("Message: " + e.getMessage());
        }
    }

    public void getOverdueLoans() {
        String query = "SELECT " +
                "    l.loan_id, " +
                "    l.copy_id, " +
                "    l.isbn, " +
                "    b.title, " +
                "    l.card_number, " +
                "    DAYS(CURRENT DATE) - DAYS(l.END_DATE) AS days_overdue, " +
                "    f.amount " +
                "FROM LOANS l " +
                "JOIN FINES f ON l.COPY_ID = f.COPY_ID " +
                "                    AND l.LOAN_ID = f.LOAN_ID " +
                "                    AND l.isbn = f.isbn " +
                "JOIN Books b ON b.isbn = l.isbn " +
                "JOIN Citizens c ON c.card_number = l.CARD_NUMBER " +
                "WHERE f.paid = 'N'";

        try(Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query)) {
            String title, isbn;
            int loan_id, copy_id, card_number, days_overdue, total_unpaid_fines;

            String format = "%-10s | %-10s | %-15s | %-40s | %-10s | %-15s | %-15s%n";
            System.out.printf(format, "Loan ID", "Copy ID", "ISBN", "Title", "Card ID", "Days Overdue", "Total Unpaid Fines");
            System.out.println("------------------------------------------------------------------------------------------------------------------------------------------");

            while (rs.next()) {
                loan_id = rs.getInt(1);
                copy_id = rs.getInt(2);
                isbn = rs.getString(3);
                title = rs.getString(4);
                card_number = rs.getInt(5);
                days_overdue = rs.getInt(6);
                total_unpaid_fines = rs.getInt(7);

                System.out.printf(format, loan_id, copy_id, isbn, title, card_number, days_overdue, total_unpaid_fines);
            }
        } catch (SQLException e) {
            int sqlCode = e.getErrorCode();
            String sqlStatement = e.getSQLState();
            System.out.println(sqlCode + " " + sqlStatement);
            System.out.println("Message: " + e.getMessage());
        }
    }
}

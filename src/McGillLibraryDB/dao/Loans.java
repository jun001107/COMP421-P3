package McGillLibraryDB.dao;

import java.sql.*;

public class Loans {
    private Connection connection;

    public Loans(Connection connection) {
        this.connection = connection;
    }

    public int addLoans(int card_num, String isbn, int copy_id) {

        return -1;
    }

    public void updateCopyStatus(String isbn, int copy_id) {

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

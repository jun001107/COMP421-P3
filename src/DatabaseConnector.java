import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {

    private static final String url = "jdbc:db2://winter2025-comp421.cs.mcgill.ca:50000/comp421";
    private static String user_id = "cs421g17";
    private static String user_pw = "CJMBNjaaa$";

    public static Connection getConnection() throws SQLException{
        // Register the driver.
        try {
            DriverManager.registerDriver (new com.ibm.db2.jcc.DB2Driver() );
        } catch (Exception cnfe) {
            System.out.println("Class not found");
        }

        if (user_id == null && (user_id = System.getenv("SOCSUSER")) == null) {
            System.err.println("Error!! do not have a user_id to connect to the database.");
        }
        if (user_pw == null && (user_pw = System.getenv("SOCSPASSWD")) == null) {
            System.err.println("Error!! do not have a password to connect to the database.");
        }
        // Establish and return the database connection
        return DriverManager.getConnection(url, user_id, user_pw);
    }
}
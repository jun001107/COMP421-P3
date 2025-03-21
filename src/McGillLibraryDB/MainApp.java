package McGillLibraryDB;

import McGillLibraryDB.dao.*;
import McGillLibraryDB.handler.*;
import McGillLibraryDB.utils.*;

import java.sql.*;

public class MainApp {
    private static Authors authorsDAO;
    public static Books booksDAO;
    public static BookSearchHandler bookSearchHandler;
    public static Citizens citizensDAO;
    public static Copies copiesDAO;
    public static CopiesHandler copiesHandler;
    public static Fines finesDAO;
    public static HasBook hasBookDAO;
    public static IsLocated isLocatedDAO;
    public static Loans loansDAO;
    public static LoanHandler loanHandler;
    public static Locations locationsDAO;
    public static Reservations reservationsDAO;
    public static Wrote wroteDAO;

    private static void initializeComponents(Connection con) {
        // Create the DAO instances using the shared connection
        authorsDAO = new Authors(con);

        booksDAO = new Books(con);
        bookSearchHandler = new BookSearchHandler(booksDAO);

        citizensDAO = new Citizens(con);

        copiesDAO = new Copies(con);
        copiesHandler = new CopiesHandler(copiesDAO);

        finesDAO = new Fines(con);
        hasBookDAO = new HasBook(con);
        isLocatedDAO = new IsLocated(con);
        loansDAO = new Loans(con);
        loanHandler = new LoanHandler(loansDAO);
        locationsDAO = new Locations(con);
        reservationsDAO = new Reservations(con);
        wroteDAO = new Wrote(con);
    }

    private static void runProgram() {
        boolean exit = false;
        int intInput;
        String strInput;

        while (!exit) {
            MenuHelper.displayMainMenu();
            intInput = UserInputHelper.getIntInput();

            switch (intInput) {
                case 1: // Search Books
                    MenuHelper.displaySearchForBooks(); // display options
                    bookSearchHandler.searchBooks(); // Return lists of books
                    break;
                case 2: // Check Book Availability
                    MenuHelper.displayCheckBookAvailability(); // display headline
                    booksDAO.getBookAvailability();
                    break;
                case 3: // Manage Copies
                    MenuHelper.displayManageCopies(); // display options
                    copiesHandler.manageCopies();
                    break;
                case 4: // Loan & Return Books
                    MenuHelper.displayLoanAndReturnBooks(); // display options
                    loanHandler.manageLoans();
                    break;
                case 5: // Generate Reports & Analytics
                    MenuHelper.displayGenerateReports(); // display options
                    break;
                case 6: // Quit
                    exit = true;
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please enter a valid option.");
            }
        }
        UserInputHelper.closeScanner();
    }

    private static void init(Connection con) {
        initializeComponents(con);
        runProgram();
    }

    public static void main(String[] args){
        // Open the connection once for the entire application
        try(Connection con = DatabaseConnector.getConnection()) {
            init(con);
        } catch (SQLException e) {
            int sqlCode = e.getErrorCode();
            String sqlState = e.getSQLState();

            // Your code to handle errors comes here;
            // something more meaningful than a print would be good
            System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
            System.out.println(e);
        }
    }
}
package McGillLibraryDB;

import McGillLibraryDB.dao.*;
import McGillLibraryDB.handler.*;
import McGillLibraryDB.utils.*;

import java.sql.*;

public class MainApp {

    public static void main(String[] args){
        // Open the connection once for the entire application
        try(Connection connection = DatabaseConnector.getConnection()) {
            DAOFactory daoFactory = new DAOFactory(connection);
            AppController appController = new AppController(daoFactory);

            boolean exit = false;
            int intInput;

            while(!exit) {
                MenuHelper.displayMainMenu();
                intInput = UserInputHelper.getIntInput();

                switch (intInput) {
                    case 1: // Search Books
                        MenuHelper.displaySearchForBooks();
                        appController.getBookSearchHandler().searchBooks();
                        break;
                    case 2: // Check Book Availability
                        MenuHelper.displayCheckBookAvailability();
                        daoFactory.getBooksDAO().getBookAvailability();
                        break;
                    case 3: // Manage Copies
                        MenuHelper.displayManageCopies();
                        appController.getCopiesHandler().manageCopies();
                        break;
                    case 4: // Loan & Return Books
                        MenuHelper.displayLoanAndReturnBooks();
                        appController.getLoanHandler().manageLoans();
                        break;
                    case 5: // Generate Reports & Analytics
                        MenuHelper.displayGenerateReports();
                        appController.getGenerateReportHandler().generateReports();
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
        } catch (SQLException e) {
            int sqlCode = e.getErrorCode();
            System.out.println("<< " + sqlCode + ": " + e.getMessage() + " >>");
        }
    }
}
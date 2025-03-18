package McGillLibraryDB;

import McGillLibraryDB.dao.*;
import McGillLibraryDB.utils.*;
import java.sql.*;

public class MainApp {

    private static void init(Connection con) {
        // Create the DAO instances using the shared connection
        Authors authorsDAO = new Authors(con);
        Books booksDAO = new Books(con);
        Citizens citizensDAO = new Citizens(con);
        Copies copiesDAO = new Copies(con);
        Fines finesDAO = new Fines(con);
        HasBook hasBookDAO = new HasBook(con);
        IsLocated isLocatedDAO = new IsLocated(con);
        Loans loansDAO = new Loans(con);
        Locations locationsDAO = new Locations(con);
        Reservations reservationsDAO = new Reservations(con);
        Wrote writeDAO = new Wrote(con);

        boolean exit = false;
        int intInput;
        String strInput;

        while (!exit) {
            MenuHelper.displayMainMenu();
            intInput = UserInputHelper.getIntInput();

            switch (intInput) {
                case 1:
                    MenuHelper.displaySearchForBooks();
                    intInput = UserInputHelper.getIntInput();

                    switch (intInput) {
                        case 1:
                            System.out.print("Enter the name of Author: ");
                            strInput = UserInputHelper.getStringInput();
                            // call SELECT book FROM Books WHERE author = strInput
                            booksDAO.getBookByAuthor(strInput);
                            // Print the output
                            break;
                        case 2:
                            System.out.print("Enter the genre: ");
                            strInput = UserInputHelper.getStringInput();
                            // call SELECT book FROM Books WHERE genre = strInput
                            booksDAO.getBookByGenre(strInput);
                            // Print the output
                            break;
                        case 3:
                            System.out.print("Enter the Title of the book: ");
                            strInput = UserInputHelper.getStringInput();
                            // call SELECT book FROM Books WHERE title = strInput
                            booksDAO.getBookByTitle(strInput);
                            // Print the output
                            break;
                        default:
                            System.out.println("Invalid option. Please enter a valid option.");
                            break;
                    }


                    break;
                case 2:
                    authorsDAO.getAuthor();
                    break;
                case 3:
                    System.out.println("Check available citizens.");
                    break;
                case 4:
                    System.out.println("Check available locations.");
                    break;
                case 5:
                    System.out.println("Check available reservations.");
                    break;
                case 6:
                    System.out.println("Check available reservations.");
                    break;
                case 7:
                    System.out.println("Check available reservations.");
                    break;
                case 8:
                    exit = true;
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please enter a valid option.");
            }
        }
        UserInputHelper.closeScanner();
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
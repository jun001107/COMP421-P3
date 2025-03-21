package McGillLibraryDB.utils;

public class MenuHelper {
    public static void displayMainMenu() {
        System.out.println("=====================================================================");
        System.out.println("Welcome to the McGill Library! What would you like to do? (Staff mode)");
        System.out.println("\t1. Search Books");
        System.out.println("\t2. Check Book Availability");
        System.out.println("\t3. Manage Copies");
        System.out.println("\t4. Loan & Return Books");
        System.out.println("\t5. Generate Reports & Analytics");
        System.out.println("\t6. Quit");
        System.out.print("Please Enter Your Choice: ");
    }

    public static void displaySearchForBooks() {
        System.out.println("\n==== Search for books =====");
        System.out.println("\t1. Search by Author name");
        System.out.println("\t2. Search by genre (Action, Adventure, Animation, Romance, Western, war, Fantasy, " +
                "Horror, Sci-Fi, Comedy, Crime, Documentary, Thriller, Children, Musical, Mystery)");
        System.out.println("\t3. Search by Title");
        System.out.println("\t4. Go Back");
        System.out.print("Please Enter Your Choice: ");
    }

    public static void displayCheckBookAvailability() {
        System.out.println("\n==== Check Book Availability =====");
    }

    public static void displayManageCopies() {
        System.out.println("\n==== Manage Copies =====");
        System.out.println("\t1. Register a New Copies");
        System.out.println("\t2. Remove a Copy");
        System.out.println("\t3. Go Back");
        System.out.print("Please Enter Your Choice: ");
    }

    public static void displayLoanAndReturnBooks() {
        System.out.println("\n==== Loan and Return Books =====");
        System.out.println("\t1. Issue a Book to a customer");
        System.out.println("\t2. Process Book Returns");
        System.out.println("\t3. View Complete Loan Transaction History");
        System.out.println("\t4. Go Back");
        System.out.print("Please Enter Your Choice: ");
    }

    public static void displayGenerateReports() {
        System.out.println("\n==== Generate Reports & Analytics =====");
        System.out.println("\t1. Popular Books Report");
        System.out.println("\t2. User Borrowing History Report");
        System.out.println("\t3. Overdue Books & Fines Report");
        System.out.println("\t4. Library Branch Performance Report");
        System.out.println("\t5. Go Back");
        System.out.print("Please Enter Your Choice: ");
    }
}

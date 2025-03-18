package McGillLibraryDB.utils;

public class MenuHelper {
    public static void displayMainMenu() {
        System.out.println("=========================================================");
        System.out.println("Welcome to the McGill Library! What would you like to do?");
        System.out.println("\t1. Search for Books");
        System.out.println("\t2. Borrow a Book");
        System.out.println("\t3. Return a Book");
        System.out.println("\t4. Pay a Fine");
        System.out.println("\t5. Reserve a Book");
        System.out.println("\t6. Manage Authors");
        System.out.println("\t7. Check Book Availability by Location");
        System.out.println("\t8. Quit");
        System.out.print("Please Enter Your Choice: ");
    }

    public static void displayBookAvailability() {
        System.out.println("\n==== Check book availability by Location ====");
        System.out.println("\t1. Please enter the location of the book");
    }

    public static void displayReturnBook() {
        System.out.println("\n==== Return a Book ====");
        System.out.println("Please enter the Copy ID for the book you are returning: ");
    }

    public static void displaySearchForBooks() {
        System.out.println("\n==== Search for books =====");
        System.out.println("\t1. Search by Author name");
        System.out.println("\t2. Search by genre (Action, Adventure, Animation, Romance, Western, war, Fantasy, " +
                "Horror, Sci-Fi, Comedy, Crime, Documentary, Thriller, Children, Musical, Mystery)");
        System.out.println("\t3. Search by Title");
        System.out.println("Please Enter Your Choice: ");
    }

    public static void displayManageAuthorsMenu() {
        System.out.println("\n==== Manage Authors ====");
        System.out.println("\t1. Add a New Author");
        System.out.println("\t2. Update an Author");
        System.out.println("\t3. Delete an Author");
        System.out.println("\t4. View an Author's Books");
        System.out.println("\t5. Return to Main Menu");
        System.out.println("Please Enter Your Choice:");
    }
}

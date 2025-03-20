package McGillLibraryDB.handler;

import McGillLibraryDB.dao.Copies;
import McGillLibraryDB.utils.UserInputHelper;

public class CopiesHandler {
    private final Copies copies;

    public CopiesHandler(Copies copies) {
        this.copies = copies;
    }

    public void manageCopies() {
        int intInput = UserInputHelper.getIntInput();
        int copy_id;
        String isbn, library_name;

        switch (intInput) {
            case 1: // Register a new copies
                System.out.println("==== Register a new copies ====");
                System.out.print("Enter the copy id: ");
                copy_id = UserInputHelper.getIntInput();
                System.out.print("Enter the isbn of the copies: ");
                isbn = UserInputHelper.getStringInput();
                System.out.print("Enter the Library name that will have the copies: ");
                library_name = UserInputHelper.getStringInput();

                int success = this.copies.addCopies(copy_id, isbn, library_name);
                if (success > 0) {
                    System.out.println("Copies registered successfully!");
                } else if (success == 0) {
                    System.out.println("Copies already registered!");
                } else {
                    System.out.println("Error registering copies!");
                }
                break;
            case 2: // Remove a copy
                System.out.println("==== Remove a copy ====");
                System.out.print("Enter the ISBN of the book that you want to remove: ");
                isbn = UserInputHelper.getStringInput();
                System.out.print("Enter the copy id of the ISBN: ");
                copy_id = UserInputHelper.getIntInput();
                // Call method
                break;
            case 3:
                break;
            default:
                System.out.println("Invalid input!");
                manageCopies();
                break;
        }
    }
    // Register a new copies

    // Remove a copy

}

package handler;

import dao.CopiesDAO;
import utils.UserInputHelper;

public class CopiesHandler {
    private final CopiesDAO copies;

    public CopiesHandler(CopiesDAO copies) {
        this.copies = copies;
    }

    public void manageCopies() {
        int intInput = UserInputHelper.getIntInput();
        int copy_id, success;
        String isbn, library_name;

        switch (intInput) {
            case 1: // Register a new copies
                System.out.println("========== Register a new copies ==========");
                System.out.print("> Enter the isbn of the copies: ");
                isbn = UserInputHelper.getStringInput();
                System.out.print("> Enter the Library name that will have the copies: ");
                library_name = UserInputHelper.getStringInput();

                success = this.copies.addCopies(isbn, library_name);
                if (success > 0) {
                    System.out.println("==> Copies registered successfully!");
                } else if (success == 0) {
                    System.out.println("==> Copies already registered!");
                } else {
                    System.out.println("==> Error registering copies!");
                }
                break;
            case 2: // Remove a copy
                System.out.println("========== Remove a copy ==========");
                System.out.print("> Enter the ISBN of the book that you want to remove: ");
                isbn = UserInputHelper.getStringInput();
                System.out.print("> Enter the copy id of the ISBN: ");
                copy_id = UserInputHelper.getIntInput();

                success = this.copies.deleteCopies(isbn, copy_id);
                if (success > 0) {
                    System.out.println("==> Copies removed successfully!");
                } else if (success == 0) {
                    System.out.println("==> Copies does not exist!");
                } else {
                    System.out.println("==> Error deleting copies!");
                }
                break;
            case 3:
                break;
            default:
                System.out.println("==> Invalid input!");
                manageCopies();
                break;
        }
    }
    // Register a new copies

    // Remove a copy

}

package McGillLibraryDB.handler;

import McGillLibraryDB.dao.Loans;
import McGillLibraryDB.utils.UserInputHelper;

public class LoanHandler {
    private final Loans loans;

    public LoanHandler(Loans loans) {
        this.loans = loans;
    }

    public void manageLoans() {
        int intInput = UserInputHelper.getIntInput();
        String isbn;
        int copyId, status;

        switch (intInput) {
            case 1: // Issue a book to a customer
                System.out.println("==== Issue a Book to a customer ====");
                System.out.print("Enter a card number: ");
                int cardNumber = UserInputHelper.getIntInput();
                System.out.print("Enter Book ISBN: ");
                isbn = UserInputHelper.getStringInput();
                System.out.print("Enter Book copy id: ");
                copyId = UserInputHelper.getIntInput();

                status = this.loans.addLoans(cardNumber, isbn, copyId);
                if (status > 0) {
                    // Handle copies = available.

                    System.out.println("==> Successfully issued a book ( ISBN: " + isbn + ", copy_id: " + copyId + ") to " + cardNumber);
                } else if (status == 0) {
                    System.out.println("==> (ISBN: " + isbn + ", copy_id: " + copyId + ") is not available.");
                } else {
                    System.out.println("==> Error on issuing a book to a customer. Please try again.");
                }
                break;
            case 2: //
                System.out.println("==== Process Book Returns ====");
                System.out.print("Enter Book ISBN: ");
                isbn = UserInputHelper.getStringInput();
                System.out.print("Enter Book copy id: ");
                copyId = UserInputHelper.getIntInput();
                // Call method
                status = this.loans.updateCopyStatus(isbn, copyId);
                if (status > 0) {
                    System.out.println("==> Completely returned book.");
                } else {
                    System.out.println("==> Error on processing a book. Please try again.");
                }
                break;
            case 3:
                System.out.println("==== View Borrowing History ====");
                // Call method
                this.loans.getAllLoans();
            case 4:
                break;
            default:
                System.out.print("==> Invalid input, please re-enter a valid option: ");
                manageLoans();
                break;
        }
    }
}

package handler;

import dao.BooksDAO;
import utils.UserInputHelper;

public class BookSearchHandler {
    private final BooksDAO booksDAO;

    public BookSearchHandler(BooksDAO booksDAO) {
        this.booksDAO = booksDAO;
    }

    public void searchBooks() {
        int intInput = UserInputHelper.getIntInput();
        String strInput;

        switch (intInput) {
            case 1:
                System.out.print("> Enter the name of Author: ");
                strInput = UserInputHelper.getStringInput();
                booksDAO.getBookByAuthor(strInput);
                break;
            case 2:
                System.out.print("> Enter the genre: ");
                strInput = UserInputHelper.getStringInput();
                booksDAO.getBookByGenre(strInput);
                break;
            case 3:
                System.out.print("> Enter the Title of the book: ");
                strInput = UserInputHelper.getStringInput();
                booksDAO.getBookByTitle(strInput);
                break;
            case 4:
                break;
            default:
                System.out.println("==> Invalid option. Please enter a valid option.");
                break;
        }
    }

}

package McGillLibraryDB.handler;

import McGillLibraryDB.dao.DAOFactory;

public class AppController {
    private final BookSearchHandler bookSearchHandler;
    private final CopiesHandler copiesHandler;
    private final LoanHandler loanHandler;
    private final GenerateReportHandler generateReportHandler;

    public AppController(DAOFactory daoFactory) {
        this.bookSearchHandler = new BookSearchHandler(daoFactory.getBooksDAO());
        this.copiesHandler = new CopiesHandler(daoFactory.getCopiesDAO());
        this.loanHandler = new LoanHandler(daoFactory.getLoansDAO());
        this.generateReportHandler = new GenerateReportHandler(daoFactory.getReportDAO());
    }

    public BookSearchHandler getBookSearchHandler() {
        return bookSearchHandler;
    }

    public CopiesHandler getCopiesHandler() {
        return copiesHandler;
    }

    public LoanHandler getLoanHandler() {
        return loanHandler;
    }

    public GenerateReportHandler getGenerateReportHandler() {
        return generateReportHandler;
    }
}

package dao;

import java.sql.Connection;

public class DAOFactory {
    private final Connection connection;

    public DAOFactory(Connection connection) {
        this.connection = connection;
    }

    public BooksDAO getBooksDAO() {
        return new BooksDAO(connection);
    }

    public CopiesDAO getCopiesDAO() {
        return new CopiesDAO(connection);
    }

    public LoansDAO getLoansDAO() {
        return new LoansDAO(connection);
    }

    public ReportDAO getReportDAO() {
        return new ReportDAO(connection);
    }
}

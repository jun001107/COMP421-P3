package McGillLibraryDB.dao;

import java.sql.*;

public class Loans {
    private Connection connection;

    public Loans(Connection connection) {
        this.connection = connection;
    }
}

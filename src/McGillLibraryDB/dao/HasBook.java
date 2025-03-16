package McGillLibraryDB.dao;

import java.sql.*;

public class HasBook {
    private Connection connection;

    public HasBook(Connection connection) {
        this.connection = connection;
    }
}

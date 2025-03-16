package McGillLibraryDB.dao;

import java.sql.*;

public class Copies {
    private Connection connection;

    public Copies(Connection connection) {
        this.connection = connection;
    }
}

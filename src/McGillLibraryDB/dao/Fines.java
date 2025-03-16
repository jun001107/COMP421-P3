package McGillLibraryDB.dao;

import java.sql.*;

public class Fines {
    private Connection connection;

    public Fines(Connection connection) {
        this.connection = connection;
    }
}

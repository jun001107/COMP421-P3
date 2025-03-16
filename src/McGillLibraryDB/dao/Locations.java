package McGillLibraryDB.dao;

import java.sql.*;

public class Locations {
    private Connection connection;

    public Locations(Connection connection) {
        this.connection = connection;
    }
}

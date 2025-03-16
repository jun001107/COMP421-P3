package McGillLibraryDB.dao;

import java.sql.*;

public class Reservations {
    private Connection connection;

    public Reservations(Connection connection) {
        this.connection = connection;
    }
}

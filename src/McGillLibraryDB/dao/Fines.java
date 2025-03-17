package McGillLibraryDB.dao;

import McGillLibraryDB.utils.UserInputHelper;

import java.sql.*;

public class Fines {
    private final Connection connection;

    public Fines(Connection connection) {
        this.connection = connection;
    }

    public void getUnpaidFinesForCitizen(int citizenId) {

    }


}

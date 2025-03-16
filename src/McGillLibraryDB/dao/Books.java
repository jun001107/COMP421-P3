package McGillLibraryDB.dao;

import java.sql.*;

public class Books {
    private Connection connection;

    public Books(Connection connection) {
        this.connection = connection;
    }


    public void addBook(String isbn, String title, String genre, int pages){

    }

}

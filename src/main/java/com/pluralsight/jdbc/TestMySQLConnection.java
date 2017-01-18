package com.pluralsight.jdbc;

import java.sql.*;

/**
 * Created by Nik on 12/28/16.
 */
public class TestMySQLConnection {

    static {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/mysql?" +
                    "user=root&password=tcp483");
            System.out.println("Connection established");
        }
        catch (java.sql.SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }
        finally {
            try {
                connection.close();
                System.out.println("Connection closed.");
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("ERROR: Failed to close connection.");
            }
        }
    }
}

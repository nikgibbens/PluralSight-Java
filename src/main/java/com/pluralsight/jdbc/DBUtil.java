package com.pluralsight.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Nik on 12/28/16.
 */
public class DBUtil {

    private static final String oraUser="hr";
    private static final String oraPwd = "oracle";
    private static final String mySqlUser = "root";
    private static final String mySqlPwd = "tcp483";
    private static final String oraCS = "jdbc:oracle:thin:@//localhost:1521/orcl";
    private static final String mySQLCS = "jdbc:mysql://localhost:3306/mysql";

    public static Connection getConnection(DBType dbType) throws SQLException {
        switch(dbType) {
            case ORADB:
                return DriverManager.getConnection(oraCS, oraUser, oraPwd);
            case MYSQLDB:
                return DriverManager.getConnection(mySQLCS, mySqlUser, mySqlPwd);
            default:
                return null;
        }
    }

    public static void showErrorMessage(SQLException se) {
        System.out.println("SQLException: " + se.getMessage());
        System.out.println("SQLState: " + se.getSQLState());
        System.out.println("VendorError: " + se.getErrorCode());
    }

}

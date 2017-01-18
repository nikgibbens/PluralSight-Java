package com.pluralsight.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import static com.pluralsight.jdbc.DBType.*;

/**
 * Created by Nik on 12/28/16.
 */
public class TestManageDBResources {

    static {
        Connection mySQLConnection = null;
        Connection oracleConnection = null;
        try {
            mySQLConnection = DBUtil.getConnection(MYSQLDB);
            System.out.println("Connection established to mySQL DB");

            oracleConnection = DBUtil.getConnection(ORADB);
            System.out.println("Connection established to Oracle DB");

        }
        catch (SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }
        finally {
            try {
                if(mySQLConnection != null) {
                    mySQLConnection.close();
                    System.out.println("mySQL DB Connection closed.");
                }
                if(oracleConnection != null) {
                    oracleConnection.close();
                    System.out.println("Oracle DB Connection closed.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("ERROR: Failed to close connection.");
            }
        }
    }

    public static void main(String[] args) {

    }
}

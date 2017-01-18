package com.pluralsight.jdbc;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DatabaseMetadataDemo {

    public static void main(String[] args) {

        try(Connection conn = DBUtil.getConnection(DBType.ORADB);) {
            DatabaseMetaData dbmd = conn.getMetaData();

            // Retrieving DAtabase Information
            System.out.println("Driver Name : " + dbmd.getDriverName());
            System.out.println("Driver Version : " + dbmd.getDriverVersion());
            System.out.println("Logged In User : " + dbmd.getUserName());
            System.out.println("Database Product Name : " + dbmd.getDatabaseProductName());
            System.out.println("Database Product Version : " + dbmd.getDatabaseProductVersion());

            // Retrieving all the table names from the connected database
            String catalog = null;
            String schemaPattern = null;
            String tableNamePattern = null;
            String schemaTypes[] = {"TABLE"};
            ResultSet rs = dbmd.getTables(catalog, schemaPattern, tableNamePattern, schemaTypes);
            System.out.println("Tables");
            System.out.println("====================");

            while(rs.next()) {
                String tableName = rs.getString("TABLE_NAME");
                System.out.println(tableName);
            }

            // Retrieving Columns present within the table
            String columnNamePattern = null;
            rs = dbmd.getColumns(catalog, schemaPattern, "NEWEMPLOYEES", columnNamePattern);
            System.out.println("Column Details for Country table");
            System.out.println("====================");
            while(rs.next()) {
                String columnName = rs.getString("COLUMN_NAME");
                System.out.println(columnName);
            }

            rs.close();

        }
        catch(SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }

    }
}

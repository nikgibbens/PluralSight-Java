package com.pluralsight.jdbc;

import java.sql.*;

/**
 * Created by Nik on 1/18/17.
 */
public class ResultSetMetadataDemo {

    public static void main(String[] args) {

        try(Connection conn = DBUtil.getConnection(DBType.ORADB);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from NewEmployees");
            ) {

            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();

            String format = "%-50s%-25s%-20s%-20s\n";
            System.out.format(format, "Column Name", "Column Type", "Is Nullable", "Is AutoIncrement");
            System.out.format(format, "----------", "----------", "----------", "----------");
            for(int i = 1; i <= columnCount; i++) {
                System.out.format(format, rsmd.getColumnName(i), rsmd.getColumnType(i), rsmd.isNullable(i), rsmd.isAutoIncrement(i));
            }

            System.out.println("Total Columns : " + columnCount);

        }
        catch(SQLException ex) {
            DBUtil.showErrorMessage(ex);
        }

    }
}

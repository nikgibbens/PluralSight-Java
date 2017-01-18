package com.pluralsight.jdbc;

import oracle.jdbc.OracleTypes;

import java.io.*;
import java.sql.*;
import java.util.Scanner;

/**
 * Created by Nik on 12/27/16.
 */
public class JDBCApplication {

    public static void main(String[] args) throws SQLException, IOException {

        System.out.println("JDBC Application");
        //mySqlConnection();
        //oracleConnection();
        //oracleExecuteStoredProc();
        //oracleExecuteBatch();
        //oracleCallableOut();
        //oracleTestCallableInOut();
        //oracleTestCallableResultSet();
        //oracleTestStoreCLOB();
        //oracleTestRetrieveCLOB();
        //oracleTestStoreBLOB();
        //oracleTestRetrieveBLOB();
    }

    // mySQL connection test
    private static void mySqlConnection() throws SQLException {
        // Open mySqlConnection
        Connection mySqlConnection = null;
        try {
            mySqlConnection = DBUtil.getConnection(DBType.MYSQLDB);
            System.out.println("Connection established.\n");

            // Do something with mySqlConnection
            if (mySqlConnection != null) {

                // Get results
                Statement statement = null;
                ResultSet resultSet = null;
                statement = mySqlConnection.createStatement();
                resultSet = statement.executeQuery("select * from user");
                resultSet.last();
                System.out.println("Total Rows : " + resultSet.getRow() + "\n");

                // Get column headers
                System.out.println("Column Headers:");
                ResultSetMetaData rsmd = resultSet.getMetaData();
                int columnCount = rsmd.getColumnCount();
                String [] columnHeaders = new String [columnCount];
                for(int i = 0; i < columnCount; i++) {
                    columnHeaders[i] = rsmd.getColumnName(i+1);
                    System.out.println(columnHeaders[i]);
                }
                System.out.println();

                // Display results
                System.out.println("Users:");
                resultSet.beforeFirst();
                while(resultSet.next()) {
                    System.out.println(resultSet.getString("User"));
                }
                System.out.println();

            }
        }
        catch(SQLException se) {
            DBUtil.showErrorMessage(se);
        }
        finally {
            // Close mySqlConnection
            try {
                if(mySqlConnection != null) {
                    mySqlConnection.close();
                    System.out.println("Connection closed.\n");
                }
            }
            catch(SQLException se) {
                System.err.println("ERROR: Unable to close mySqlConnection!");
                throw se;
            }
        }
    }

    // oracleConnection test
    private static void oracleConnection() {

        try(Connection oracleConnection = DBUtil.getConnection(DBType.ORADB)){

            System.out.println("Oracle DB connection opened.\n");
            System.out.println("Countries:");
            String sql = "select * from countries";
            Statement statement = oracleConnection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()) {
                System.out.println(resultSet.getString("COUNTRY_NAME"));
            }
        }
        catch(SQLException se) {
            DBUtil.showErrorMessage(se);
        }
        finally {
            System.out.println("\nOracle DB connection closed.");
        }

    }

    // Prepare statement to add new employee
    private static void prepareAddEmployeeCallableStatement(CallableStatement callableStatement) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter Employee # : ");
            int empno = Integer.parseInt(scanner.nextLine());
            System.out.println("Enter First Name : ");
            String fname = scanner.nextLine();
            System.out.println("Enter Last Name : ");
            String lname = scanner.nextLine();
            System.out.println("Enter email : ");
            String email = scanner.nextLine();
            System.out.println("Enter Hire date : ");
            java.sql.Date hire = java.sql.Date.valueOf(scanner.nextLine());
            System.out.println("Enter Job ID : ");
            String jid = scanner.nextLine();

            callableStatement.setInt(1, empno);
            callableStatement.setString(2, fname);
            callableStatement.setString(3, lname);
            callableStatement.setString(4, email);
            callableStatement.setDate(5, hire);
            callableStatement.setString(6, jid);
        }
        catch(SQLException se) {
            DBUtil.showErrorMessage(se);
        }
    }

    // Oracle DB Stored procedure test
    private static void oracleExecuteStoredProc() {
        try(
                Connection connection = DBUtil.getConnection(DBType.ORADB);
                CallableStatement callableStatement = connection.prepareCall("{call AddNewEmployee(?, ?, ?, ?, ?, ?)}");
                )
        {
            prepareAddEmployeeCallableStatement(callableStatement);
            callableStatement.execute();
            System.out.println("Employee record added successfully.");
        }
        catch(SQLException se) {
            DBUtil.showErrorMessage(se);
        }
    }

    // Oracle DB Batch Process test
    private static void oracleExecuteBatch() {
        try(
                Connection connection = DBUtil.getConnection(DBType.ORADB);
                CallableStatement callableStatement = connection.prepareCall("{call AddNewEmployee(?, ?, ?, ?, ?, ?)}");
                Scanner scanner = new Scanner(System.in)
        )
        {
            String option;
            do {
                prepareAddEmployeeCallableStatement(callableStatement);
                callableStatement.addBatch();
                System.out.println("Do you want to add another employee?");
                option = scanner.nextLine();
            } while(option.equals("yes"));

            int [] updateCounts = callableStatement.executeBatch();
            System.out.println("Total Records Inserted are : " + updateCounts.length);
        }
        catch(SQLException se) {
            DBUtil.showErrorMessage(se);
        }
    }

    // Oracle Callable Out test
    private static void oracleCallableOut() {
        try (
                Connection connection = DBUtil.getConnection(DBType.ORADB);
                CallableStatement callableStatement = connection.prepareCall("{ call GetTotalEmployeesByDepartment(?, ?) }");
                Scanner scanner = new Scanner(System.in)
                )
        {
            System.out.println("Enter Department ID : ");
            int deptno = Integer.parseInt(scanner.nextLine());

            callableStatement.setInt(1, deptno);
            callableStatement.registerOutParameter(2, Types.INTEGER);
            callableStatement.execute();
            int totalEmployees = callableStatement.getInt(2);

            System.out.println("Total Employees Working : " + totalEmployees);

        }
        catch(SQLException se) {
            DBUtil.showErrorMessage(se);
        }
    }

    // Oracle Callable In Out test
    private static void oracleTestCallableInOut() throws SQLException {

//        Connection connection = null;
//        CallableStatement callableStatement = null;
//        Scanner scanner = null;
//
//        try {
//            connection = DBUtil.getConnection(DBType.ORADB);
//            callableStatement = connection.prepareCall("{ call GetEmployeesById(?, ?) }");
//            scanner = new Scanner(System.in);
//            System.out.print("Enter Department ID : ");
//            int depid = Integer.parseInt(scanner.nextLine());
//
//
//        }
//        catch(SQLException se) {
//            DBUtil.showErrorMessage(se);
//        }
//        finally {
//            scanner.close();
//            callableStatement.close();
//            connection.close();
//        }

    }

    // Oracle test callable statement with result set returned
    private static void oracleTestCallableResultSet() {

        try (
                Connection connection = DBUtil.getConnection(DBType.ORADB);
                CallableStatement callableStatement = connection.prepareCall("{call GetEmployeesByRefCursor(?, ?)}");
                Scanner scanner = new Scanner(System.in)
                ) {

            System.out.println("Enter Department ID : ");
            int deptno = Integer.parseInt(scanner.nextLine());

            callableStatement.setInt(1, deptno);
            callableStatement.registerOutParameter(2, OracleTypes.CURSOR);
            callableStatement.execute();
            ResultSet resultSet = ((oracle.jdbc.internal.OracleCallableStatement)callableStatement).getCursor(2);

            String format = "%-4s%-50s%-25s%-10f\n";
            while(resultSet.next()) {
                System.out.format(format, resultSet.getString("Employee_ID"), resultSet.getString("Employee_Name"),
                        resultSet.getString("Email"), resultSet.getFloat("Salary"));
            }
        }
        catch(SQLException se) {
            DBUtil.showErrorMessage(se);
        }
    }

    // Store CLOB
    private static void oracleTestStoreCLOB() throws SQLException, IOException {

        Connection connection = DBUtil.getConnection(DBType.ORADB);
        PreparedStatement pstmt = null;

        String sql = "Update NewEmployees set Resume = ? where Employee_ID = 888";
        pstmt = connection.prepareStatement(sql);
        File file = new File("/Users/Nik/temp/nik_resume.rtf");
        FileReader reader = new FileReader(file);

        pstmt.setCharacterStream(1, reader, (int)file.length());
        pstmt.executeUpdate();

        System.out.println("Resume Updated Successfully...");
        pstmt.close();
        reader.close();
        connection.close();
    }

    // Retrieve CLOB
    private static void oracleTestRetrieveCLOB() throws SQLException, IOException {

        Connection connection = DBUtil.getConnection(DBType.ORADB);
        String sql = "select Resume from NewEmployees where Employee_ID = 888";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        if(rs.next()) {
            Clob resume = rs.getClob("Resume");
            Reader data = resume.getCharacterStream();

            int i;
            String resumeDetails = "";
            while((i = data.read()) != -1) {
                resumeDetails += ((char)i);
            }
            System.out.println("Resume Details for Employee 888");
            System.out.println(resumeDetails);
            data.close();
        }
        else {
            System.err.println("No record found for employee with ID 888.");
        }

        rs.close();
        pstmt.close();
        connection.close();
    }

    // Store BLOB
    private static void oracleTestStoreBLOB() throws SQLException, IOException {

        Connection connection = DBUtil.getConnection(DBType.ORADB);
        String sql = "update NewEmployees set Photo = ? where Employee_ID = 888";
        PreparedStatement pstmt = connection.prepareStatement(sql);

        File file = new File("/Users/Nik/Pictures/Profile/IMG_2162-2.jpg");
        FileInputStream fis = new FileInputStream(file);

        pstmt.setBinaryStream(1, fis, file.length());

        int count = pstmt.executeUpdate();
        System.out.println("Total records updated: " + count);
        fis.close();
        pstmt.close();
        connection.close();

    }

    // Retrieve BLOB
    private static void oracleTestRetrieveBLOB() throws SQLException, IOException {

        Connection connection = DBUtil.getConnection(DBType.ORADB);
        String sql = "select Photo from NewEmployees where Employee_Id = 888";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();

        if(rs.next()) {
            Blob imgBlob = rs.getBlob("Photo");
            FileOutputStream fos = new FileOutputStream("/Users/Nik/Pictures/Profile/IMG_2162-3.jpg");
            fos.write(imgBlob.getBytes(1, (int)imgBlob.length()));
            fos.flush();
            fos.close();
            System.out.println("Photo of employee 888 has been Downloaded successfully.");
        }
        else {
            System.err.println("Employee record not found.");
        }

        rs.close();
        pstmt.close();
        connection.close();
    }
}

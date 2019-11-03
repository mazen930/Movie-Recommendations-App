package com.example.android.break2;
import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
public class DBManager {
    String ip = "192.168.137.1";
    String classs = "net.sourceforge.jtds.jdbc.Driver";
    String db = "movies_database";
    String un = "sa"; // your server username ; like localhost
    String password = "123"; // your password of dbms
    Connection connectionObj = null; // connection object to handle the quires
    //////////////////////////////////////**/

    @SuppressLint("NewApi")
    public Connection openConnection() {
        if (connectionObj != null) return connectionObj;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection conn = null;
        String ConnURL = null;
        try {

            Class.forName(classs);
            ConnURL = "jdbc:jtds:sqlserver://" + ip + ";"
                    + "databaseName=" + db + ";user=" + un + ";password="
                    + password + ";";
            conn = DriverManager.getConnection(ConnURL);
        } catch (SQLException se) {
            Log.e("ERRO", se.getMessage());
        } catch (ClassNotFoundException e) {
            Log.e("ERRO", e.getMessage());
        } catch (Exception e) {
            Log.e("ERRO", e.getMessage());
        }
        return conn;
    } // Constructor " Connection Starting "

    public void closeConnection() {
        try {
            connectionObj.close();
        } catch (Exception e) {
            Log.i("Closing Exception", e.getMessage());
        }
    } // Closing Connection

    public int ExecuteNonQuery(String Query) {
        int isSuccess = 0;
        connectionObj = openConnection();
        ResultSet rs = null;

        try {
            Statement stmt = connectionObj.createStatement();
            isSuccess = stmt.executeUpdate(Query);
        } catch (SQLException e) {
            Log.i("function test ", e.getMessage());
        }
        return isSuccess;
    }

    public String ExecuteScalar(String Query)throws SQLException {
        connectionObj = openConnection();
        try {
            if (connectionObj == null) {
                Log.i("ReaderQueryError", "Error in connection with SQL server");
            } else {
                Statement statement = connectionObj.createStatement();
                ResultSet rs = statement.executeQuery(Query);
                if(rs.next())
                    return rs.getString(1);

            }

        } catch (Exception ex) {
            Log.i("ScalarQueryError", "Error in Scalar Query Execution");
        }
        return "0";
    } // Scalar Quires

    public ResultSet ExecuteReader(String Query) {
        connectionObj = openConnection();
        try {

            Statement stmt = connectionObj.createStatement();
            ResultSet rs = stmt.executeQuery(Query);
            return rs;
        } catch (SQLException e) {
            Log.i("Reader execution ", e.getMessage());
        }
        return null;
    }
}
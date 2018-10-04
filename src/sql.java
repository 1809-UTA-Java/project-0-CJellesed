package com.revature;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.io.*;

class sql {
    
    static Connection connection = null;
    static void disconnect() {
        try {
            connection.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
    static void connect() {
        //System.out.println("-------- Oracle JDBC Connection Testing ------");

        try {
            DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
        } catch (SQLException e) {
            System.out.println("Where is your Oracle JDBC Driver?");
            e.printStackTrace();
            return;
        }

        //System.out.println("Oracle JDBC Driver Registered!");

        try {

            connection = DriverManager.getConnection(
                    "jdbc:oracle:thin:@192.168.56.105:1521:xe", "bank", "bank");

        } catch (SQLException e) {

            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return;

        }

        if (connection != null) {
            System.out.println("\nConnection Successful");
        } else {
            System.out.println("\nFailed to connect");
        }
    }
    
    static void getFunds(String uName, String uPassword) throws SQLException {
        Statement stmt = null;
        String funds;
        String query = "select accnum, (select abalance from accounts where accounts.anum = users.accnum) as temp from users where users.uname = '" + uName + "' and users.password = '" + uPassword + "'";
        //String query2 = "select accnum, (select abalance from accounts where accounts.anum = users.accnum) as temp from users where users.uname = 'CJellesed' and users.password = 'pswrd'";
        try {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if(rs == null)
                System.out.println("\nInvalid Account Info");
            while (rs.next()) {
                System.out.println("\nAccount: " + rs.getString("ACCNUM"));
                // remove this var if you decide not to pass anything back.
                funds = rs.getString("TEMP");
                System.out.println("Current Balance: " + funds);
                banking.Continue();
            }
        } catch (SQLException e ) {
            e.printStackTrace();
        } finally {
            if (stmt != null) { stmt.close(); }
        }
    }
} 
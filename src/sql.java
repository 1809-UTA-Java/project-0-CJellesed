package com.revature;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

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
        System.out.println("-------- Oracle JDBC Connection Testing ------");

        try {
            DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
        } catch (SQLException e) {
            System.out.println("Where is your Oracle JDBC Driver?");
            e.printStackTrace();
            return;
        }

        System.out.println("Oracle JDBC Driver Registered!");

        try {

            connection = DriverManager.getConnection(
                    "jdbc:oracle:thin:@192.168.56.105:1521:xe", "bank", "bank");

        } catch (SQLException e) {

            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return;

        }

        if (connection != null) {
            System.out.println("Connected!");
        } else {
            System.out.println("Failed to connect");
        }
    }
    
    static void getFunds() throws SQLException {
        Statement stmt = null;
        String name = "CJellesed", funds = "";
        String query = "select * from users where userName = 'CJellesed'";
        try {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                System.out.println("In while");
                System.out.println(rs.getString("lName"));
                //int supplierID = rs.getInt("SUP_ID");
                //float price = rs.getFloat("PRICE");
                //int sales = rs.getInt("SALES");
                //int total = rs.getInt("TOTAL");
                funds = rs.getString("fName");
                System.out.println(funds);
            }
        } catch (SQLException e ) {
            e.printStackTrace();
        } finally {
            if (stmt != null) { stmt.close(); }
        }
    }
} 
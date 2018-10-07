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

    static boolean isConnected() {
        if(connection != null)
            return true;
        else
            return false;
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
            System.out.println("Connection Successful");
        } else {
            System.out.println("\nFailed to connect");
        }
    }
    
    static String getFunds(String uName, String uPassword) throws SQLException {
        Statement stmt = null;
        String funds = "";
        String query = "select accnum, (select abalance from accounts where accounts.anum = users.accnum) as temp from users where users.uname = '" + uName + "' and users.password = '" + uPassword + "'";
        try {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            //With the account implementation this if should never be hit.
            if(!rs.isBeforeFirst())
                System.out.println("\nInvalid Account Info");
            while (rs.next()) {
                System.out.println("\nAccount: " + rs.getString("ACCNUM"));
                // remove this var if you decide not to pass anything back.
                funds = rs.getString("TEMP");
                System.out.println("Current Balance: " + funds);
                //banking.Continue();
            }
        } catch (SQLException e ) {
            e.printStackTrace();
        } finally {
            if (stmt != null) { stmt.close(); }
        }
        return funds;
    }

    static void deposit(String money, int account) throws SQLException {
        Statement stmt = null;
        String query = "update accounts set abalance = abalance + " + money + " where anum =  " + account;
        try {
            stmt = connection.createStatement();
            //stmt.execute(query);
            ResultSet rs = stmt.executeQuery(query);
            if(!rs.isBeforeFirst()) {
                System.out.println("Deposit failed?");
            }
            else
                System.out.println("\nDeposit Successful");
        } catch (SQLException e ) {
            e.printStackTrace();
        } finally {
            if (stmt != null) { stmt.close(); }
        }
    }

    //This could be merged with deposit, but oh well. 
    static void withdraw(String money, int account) throws SQLException {
        Statement stmt = null;
        String query = "update accounts set abalance = abalance - " + money + " where anum =  " + account;
        try {
            stmt = connection.createStatement();
            //stmt.execute(query);
            ResultSet rs = stmt.executeQuery(query);
            if(!rs.isBeforeFirst()) {
                System.out.println("Withdraw failed?");
            }
            else
                System.out.println("\nWithdraw Successful");
        } catch (SQLException e ) {
            e.printStackTrace();
        } finally {
            if (stmt != null) { stmt.close(); }
        }
    }

    static account login(String uName, String uPassword) throws SQLException {
        account ac = new account();
        Statement stmt = null;
        String name = "";
        String query = "select * from users where uname = '" + uName + "' and password = '" + uPassword + "'";
        try {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if(!rs.isBeforeFirst())
                System.out.println("\nInvalid Account Info");
            while (rs.next()) {;
                // remove this var if you decide not to pass anything back.
                ac.aName = uName;
                ac.password = uPassword;
                ac.fName = rs.getString("fName");
                ac.pLevel = rs.getInt("ulevel");
                ac.aNum = rs.getInt("accnum");
                System.out.println("Welcome: " + name);
            }
        } catch (SQLException e ) {
            e.printStackTrace();
        } finally {
            if (stmt != null) { stmt.close(); }
        }
        return ac;
    }

    static String createAccount(String uName, String uPassword, String fName, String lName, String email) throws SQLException {
        Statement stmt = null;
        String name = "";
        String query = "insert into users (uname, fname, lname, email, password) values ('" + uName  + "','" + fName + "','" + lName + "','" + email + "','" + uPassword + "')";
        try {
            stmt = connection.createStatement();
            stmt.execute(query);
        } catch (SQLException e ) {
            e.printStackTrace();
        } finally {
            if (stmt != null) { stmt.close(); }
        }
        return name;
    }

    static boolean checkColumn(String column, String value) throws SQLException {
        boolean free = false;
        Statement stmt = null;
        String query = "Select " + column + " from users where " + column + " = '" + value + "'";
        try {
            stmt = connection.createStatement();
            //stmt.execute(query);
            ResultSet rs = stmt.executeQuery(query);
            if(!rs.isBeforeFirst()) {
                System.out.println("Entered option is free");
                free = true;
            }
            else
                System.out.println("\nUsername is taken");
        } catch (SQLException e ) {
            e.printStackTrace();
        } finally {
            if (stmt != null) { stmt.close(); }
        }
        return free;
    }

    static boolean isActive(String user) throws SQLException {
        boolean active = true;
        Statement stmt = null;
        String query = "Select active from users where uname = '" + user + "' and active = 1";
        try {
            stmt = connection.createStatement();
            //stmt.execute(query);
            ResultSet rs = stmt.executeQuery(query);
            if(!rs.isBeforeFirst()) {
                System.out.println("Account is not active");
                active = false;
            }
        } catch (SQLException e ) {
            e.printStackTrace();
        } finally {
            if (stmt != null) { stmt.close(); }
        }
        return active;
    }

    static void joinAccount(String userName, String aName) throws SQLException {
        Statement stmt = null;
        String query = "update users set merge = (select accnum from users where uname = '" + userName + "') where uname = '" + aName + "'";
        try {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if(!rs.isBeforeFirst()) {
                System.out.println("User not found");
            }
        } catch (SQLException e ) {
            e.printStackTrace();
        } finally {
            if (stmt != null) { stmt.close(); }
        }
    }
} 
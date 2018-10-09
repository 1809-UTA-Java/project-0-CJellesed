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
    
    static String getFunds(String uName, String uPassword, int level) throws SQLException {
        Statement stmt = null;
        String funds = "", query = "";
        if(level > 0)
            query = "select accnum, (select abalance from accounts where accounts.anum = users.accnum) as temp from users where users.uname = '" + uName + "'";
        else
            query = "select accnum, (select abalance from accounts where accounts.anum = users.accnum) as temp from users where users.uname = '" + uName + "' and users.password = '" + uPassword + "'";
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

    static boolean deposit(String money, int account) throws SQLException {
        Statement stmt = null;
        boolean found = true;
        String query = "update accounts set abalance = abalance + " + money + " where anum =  " + account;
        try {
            stmt = connection.createStatement();
            //stmt.execute(query);
            ResultSet rs = stmt.executeQuery(query);
            if(!rs.isBeforeFirst()) {
                System.out.println("Deposit failed");
                found = false;
            }
            else
                System.out.println("\nDeposit Successful");
        } catch (SQLException e ) {
            e.printStackTrace();
        } finally {
            if (stmt != null) { stmt.close(); }
        }
        return found;
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
            ResultSet rs = stmt.executeQuery(query);
            if(!rs.isBeforeFirst()) {
                System.out.println("Existing user not found");
                free = true;
            }
            else
                System.out.println("\nExisting user found");
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

    static boolean joinAccount(String userName, String aName) throws SQLException {
        boolean joined = true;
        Statement stmt = null;
        String query = "update users set merge = (select accnum from users where uname = '" + userName + "') where uname = '" + aName + "'";
        try {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if(!rs.isBeforeFirst()) {
                System.out.println("User not found");
                joined = false;
            }
        } catch (SQLException e ) {
            e.printStackTrace();
        } finally {
            if (stmt != null) { stmt.close(); }
        }
        return joined;
    }

    
    static account showUsers(String user, boolean all) throws SQLException {
        account ac = new account();
        Statement stmt = null;
        String query = "", lname = "", email = "";
        int active = 0, merge = 0;
        if(all)
            query = "select * from users";
        else
            query = "select * from users where uname = '" + user + "'";
        try {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if(!rs.isBeforeFirst())
                System.out.println("\nNo data found");
            while (rs.next()) {;
                ac.aName = rs.getString("uname");
                ac.password = rs.getString("password");
                ac.fName = rs.getString("fName");
                ac.pLevel = rs.getInt("ulevel");
                ac.aNum = rs.getInt("accnum");
                lname = rs.getString("lname");
                email = rs.getString("email");
                active = rs.getInt("active");
                merge = rs.getInt("merge");
                System.out.println("\nAccount [UserName=" + ac.aName + ", FirstName=" + ac.fName + ", LastName=" + lname + ", AccountNumber=" + ac.aNum + ", Email=" + email + ", Password=" + ac.password + ", UserLevel=" + ac.pLevel + ", Active=" + active + ", Merge=" + merge);
            }
        } catch (SQLException e ) {
            e.printStackTrace();
        } finally {
            if (stmt != null) { stmt.close(); }
        }
        return ac;
    }

    static void setAccount(String user, int val, int level) throws SQLException {
        Statement stmt = null;
        String query = "";
        if(level == 2)
            query = "update users set active = " + val + " where uname = '" + user + "'";
        else
            query = "update users set active = " + val + " where uname = '" + user + "' and active = 0";
        try {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if(!rs.isBeforeFirst()) {
                System.out.println("Account not found");
            }
        } catch (SQLException e ) {
            e.printStackTrace();
        } finally {
            if (stmt != null) { stmt.close(); }
        }
    }
} 
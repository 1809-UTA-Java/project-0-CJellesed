package com.revature;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.io.*;
import org.apache.log4j.Logger;
//import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.*;


class sql {
    public static Logger logger = Logger.getLogger(sql.class);
    static Connection connection = null;

    /**
     * clossed the connection to the database.
     */
    static void disconnect() {
        try {
            connection.close();
        } catch(SQLException e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * checks for a valid connection and returns a bool
     * @return returns true if connected already,
     */
    static boolean isConnected() {
        if(connection != null)
            return true;
        else
            return false;
    }

    /**
     * created the database connection with JDBC and the bank account
     */
    static void connect() {
        BasicConfigurator.configure();
        //logger.setLevel(Level.OFF);
            
        //System.out.println("-------- Oracle JDBC Connection Testing ------");

        try {
            DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
        } catch (SQLException e) {
            System.out.println("Where is your Oracle JDBC Driver?");
            logger.error(e.getMessage());
            return;
        }

        //System.out.println("Oracle JDBC Driver Registered!");

        try {

            connection = DriverManager.getConnection(
                    "jdbc:oracle:thin:@192.168.56.105:1521:xe", "bank", "bank");

        } catch (SQLException e) {

            System.out.println("Connection Failed! Check output console");
            logger.error(e.getMessage());
            return;

        }

        if (connection != null) {
            //System.out.println("Connection Successful");
            logger.info("Sucessfully Connected");
        } else {
            logger.error("Failed to connect");
            //System.out.println("\nFailed to connect");
        }
    }
    
    /**
     * has two queries one for normal users and another for employees or admins. rerns the requested users account balance.
     * @param uName is pulled from the value stored in accounts when customers call this or is passed in by a employee account
     * @param uPassword is pulled from account or can be "" or anything technically if an employee or admin calls this.
     * @param level used to choose which query to use. should be 0 1 or 2
     * @return returns a string with the account balance in it.
     * @throws SQLException 
     */
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
            if(!rs.isBeforeFirst())
                System.out.println("\nInvalid Account Info");
            while (rs.next()) {
                System.out.println("\nAccount: " + rs.getString("ACCNUM"));
                funds = rs.getString("TEMP");
                System.out.println("Current Balance: " + funds);
            }
        } catch (SQLException e ) {
            logger.error(e.getMessage());
        } finally {
            if (stmt != null) { stmt.close(); }
        }
        return funds;
    }

    /**
     * takes an ammount and account number and inserts the money into the users account in the database.
     * @param money value to be stored to the account balance in the database
     * @param account account number in the database
     * @return bool for id the account number existed.
     * @throws SQLException
     */
    static boolean deposit(String money, int account) throws SQLException {
        Statement stmt = null;
        boolean found = true;
        String query = "update accounts set abalance = abalance + " + money + " where anum =  " + account;
        try {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if(!rs.isBeforeFirst()) {
                //System.out.println("Deposit failed");
                logger.error("Deposit Failed");
                found = false;
            }
            else
                logger.info("Deposit Successful");
                //System.out.println("\nDeposit Successful");
        } catch (SQLException e ) {
            logger.error(e.getMessage());
        } finally {
            if (stmt != null) { stmt.close(); }
        }
        return found;
    }

    /**
     * takes a money valu and an account number. removes the desired ammount from the database.
     * @param money value to be removed
     * @param account account to take from
     * @throws SQLException
     */
    static void withdraw(String money, int account) throws SQLException {
        Statement stmt = null;
        String query = "update accounts set abalance = abalance - " + money + " where anum =  " + account;
        try {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if(!rs.isBeforeFirst()) {
                //System.out.println("Withdraw failed");
                logger.error("Withdraw Failed");
            }
            else {
                logger.info("Withdraw Successful");
                //System.out.println("\nWithdraw Successful");
            }
        } catch (SQLException e ) {
            logger.error(e.getMessage());
        } finally {
            if (stmt != null) { stmt.close(); }
        }
    }

    /**
     * takes a username and password. this works if it finds an anctive account and returns an empty account
     * object if the user was not found or was inactive.
     * @param uName username in the database
     * @param uPassword password in the database
     * @return an account object that has all values filled if the user was found or is empty.
     * @throws SQLException
     */
    static account login(String uName, String uPassword) throws SQLException {
        account ac = new account();
        Statement stmt = null;
        String name = "";
        String query = "select * from users where uname = '" + uName + "' and password = '" + uPassword + "' and active = 1";
        try {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if(!rs.isBeforeFirst())
                System.out.println("\nInvalid or Inactive Account");
            while (rs.next()) {;
                ac.aName = uName;
                ac.password = uPassword;
                ac.fName = rs.getString("fName");
                ac.pLevel = rs.getInt("ulevel");
                ac.aNum = rs.getInt("accnum");
                System.out.println("Welcome: " + name);
            }
        } catch (SQLException e ) {
            logger.error(e.getMessage());
        } finally {
            if (stmt != null) { stmt.close(); }
        }
        return ac;
    }

    /**
     * Takes all values and passes them into the database. These values should works as they were checked before being passed in.
     * @param uName works and the primary key in the database
     * @param uPassword
     * @param fName
     * @param lName
     * @param email
     * @return THis probably needs to be changed.
     * @throws SQLException
     */
    static String createAccount(String uName, String uPassword, String fName, String lName, String email) throws SQLException {
        Statement stmt = null;
        String name = "";
        String query = "insert into users (uname, fname, lname, email, password) values ('" + uName  + "','" + fName + "','" + lName + "','" + email + "','" + uPassword + "')";
        try {
            stmt = connection.createStatement();
            stmt.execute(query);
            logger.info("Account Created");
        } catch (SQLException e ) {
            logger.error(e.getMessage());;
        } finally {
            if (stmt != null) { stmt.close(); }
        }
        return name;
    }

    /**
     * is called when an employee aproves a new account. this function adds a row to accounts where money can be stored. 
     * getFunds() would return a null before this point and all other functions would fail. Sets balance to 0.
     */
    static String createMoneyAccount(String uName, Double num) throws SQLException {
        Statement stmt = null;
        String name = "";
        String query = "insert into accounts values ((select accnum from users where uname = '" + uName + "'), " + num + ")";
        try {
            stmt = connection.createStatement();
            stmt.execute(query);
            logger.info("Account Created");
        } catch (SQLException e ) {
            logger.error(e.getMessage());;
        } finally {
            if (stmt != null) { stmt.close(); }
        }
        return name;
    }

    /**
     * this takes a column name from the database and a value to check for. Note that the value returns false if it is found as the
     * check was initially used to look for unused usernames.
     * @param column
     * @param value
     * @return returns false if a match is found.
     * @throws SQLException
     */
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
            logger.error(e.getMessage());
        } finally {
            if (stmt != null) { stmt.close(); }
        }
        return free;
    }

    /**
     * returns true or false based on if the passed username has an active account.
     * @param user
     * @return returns true or false
     * @throws SQLException
     */
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
            logger.error(e.getMessage());
        } finally {
            if (stmt != null) { stmt.close(); }
        }
        return active;
    }

    /**
     * 
     * @param userName
     * @param aName
     * @return
     * @throws SQLException
     */
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
            logger.error(e.getMessage());
        } finally {
            if (stmt != null) { stmt.close(); }
        }
        return joined;
    }

    
    static account showUsers(String user, int val) throws SQLException {
        account ac = new account();
        Statement stmt = null;
        String query = "", lname = "", email = "";
        int active = 0, merge = 0;

        if(val == 1)
            query = "select * from users where ulevel = 0";
        else if(val == 2)
            query = "select * from users where uname = '" + user + "' and ulevel = 0";
        else if(val == 3)
            query = "select * from users where merge <> 0";

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
            logger.error(e.getMessage());
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
            logger.error(e.getMessage());
        } finally {
            if (stmt != null) { stmt.close(); }
        }
    }

    static void setAccountNum(String userTwo, int accOne) throws SQLException {
        Statement stmt = null;
        String query = "";

        query = "update users set accnum = " + accOne + " where uname = '" + userTwo + "'";
        try {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if(!rs.isBeforeFirst()) {
                System.out.println("Account not found");
            }
        } catch (SQLException e ) {
            logger.error(e.getMessage());
        } finally {
            if (stmt != null) { stmt.close(); }
        }
    }
} 
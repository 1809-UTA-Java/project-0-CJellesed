package com.revature;
//import sql.java;

import java.sql.SQLException;
import java.util.*;
import java.io.*;

class account {
        protected String aName, fName, password;
        protected int pLevel, aNum;
        // I think a default constructor should be left out, because we want no empty accounts. Check later.
        protected account() {
            this.aName = "";
            this.password = "";
            this.fName = "";
            this.pLevel = 0;
            this. aNum = 0;
        }

        protected account(String aName, String password, String fName, int pLevel, int aNum) {
            this.aName = aName;
            this.password = password;
            this.fName = fName;
            this.pLevel = pLevel;
            this. aNum = aNum;
        }

        protected boolean login() {
            boolean loggedIn = true;
            System.out.println("\nEnter Username");
            aName = getString();
            System.out.println("\nEnter Password");
            password = getString();

            System.out.println("\nConnecting:");
            
            if(!sql.isConnected())
                sql.connect();
            try { fName = sql.login(aName, password); }
            catch(SQLException e) {
                e.printStackTrace();
            }
            //If the login was bad, Reset the name and password to empty. aName is currently checked for valid logins.
            // aName == "" is === to Logged out atm.
            if(fName == "") {
                aName = "";
                password = "";
                loggedIn = false;
            }
            return loggedIn;
        }
        protected void depositeFunds() {
            //Make sure to write this method
            // Also make sure to change 3 from a hardcoded value ie rework login()
            boolean active = false;
            try {active = sql.isActive(aName);}
            catch(SQLException e) {
                e.printStackTrace();
            }
            if(active) {
                String ammount = getAmmount(0);
                if(!sql.isConnected())
                    sql.connect();
                try { 
                    sql.deposit(ammount, 3);
                    System.out.println("New Balance is");
                    sql.getFunds(aName, password);
                }
                catch(SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        protected void withdrawFunds() { 
            // Also make sure to change 3 from a hardcoded value ie rework login()
            boolean active = false;
            try {active = sql.isActive(aName);}
            catch(SQLException e) {
                e.printStackTrace();
            }
            if(active) {
                String ammount = getAmmount(1);
                if(!sql.isConnected())
                    sql.connect();
                try {
                    sql.withdraw(ammount, 3);
                    System.out.println("New Balance is");
                    sql.getFunds(aName, password);
                }
                catch(SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        protected void transferFunds() {
            // Also make sure to change 3 from a hardcoded value ie rework login()
            String ammount = getAmmount(1);
            System.out.println("Enter account number to tranfer to.");
            //String where = getString();
            if(!sql.isConnected())
                sql.connect();
            try { 
                sql.withdraw(ammount, 3);
                sql.deposit(ammount, 2);
                System.out.println("New Balance is");
                sql.getFunds(aName, password);
            }
            catch(SQLException e) {
                e.printStackTrace();
            }
        }
        protected void viewFunds() {
            if(!sql.isConnected())
                sql.connect();
            try { 
                sql.getFunds(aName, password);
                banking.Continue();
            }
            catch(SQLException e) {
                e.printStackTrace();
            }
        }

        void createAccount() {
            //Work on the getter and setters here.
            String uname = "", password = "", fname = "", lname = "", email = "";
            boolean free = false;
            while (!free) {
                System.out.println("\nEnter Username.");
                uname = getString();
                System.out.println("\nChecking availability");
                if(!sql.isConnected())
                    sql.connect();
                try { free = sql.checkColumn("uname", uname); }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            free = false;
            while(!free) {
                System.out.println("\nEnter email");
                email = getString();
                System.out.println("\nChecking availability");
                if(!sql.isConnected())
                    sql.connect();
                try { free = sql.checkColumn("email", email); }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            System.out.println("Enter a password");
            password = getString();
            System.out.println("Enter First Name");
            fname = getString();
            System.out.println("Enter Last Name");
            lname = getString();

            try { 
                sql.createAccount(uname, password, fname, lname, email);
                System.out.println("Account Created");
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }

        protected void joinAccount() {
            
        }

        // Currently returns one line Strings from the user.
        static String getString() {
         
            Scanner AccOp = new Scanner(System.in);
            String userIn = "";
		    try{userIn = AccOp.nextLine();}
		    catch(InputMismatchException err) {
			System.out.println("Some Error\n");
		    }
            return userIn;
        }

        //Check should be 0 or 1. 0 for depossit nad 1 for any kind of withdrawal.
        String getAmmount(int check) {
            boolean valid = false, cont = true;
            double number = 0, balance = 0;
            String ammount = "", cbalance = "";

            while(!valid && cont) {
                valid = true;

                System.out.println("\nEnter ammount");
                ammount = getString();

                System.out.println(ammount);

                try { number = Double.parseDouble(ammount); }
                catch(Exception e) {
                    System.out.println("\nInvalid input. Enter in the form of 50.00 or 5000.00 or 500\nMake sure to leave out commas and $");
                    System.out.println("\nEnter another value or type exit"); 
                    if(ammount.equals("exit")) {
                        cont = false;
                        break;
                    }
                    valid = false;
                }
                if (number < 0) {
                    System.out.println("Value must be possitive.");
                    valid = false;
                }
                //Checks to keep from overdrawing.
                if(check == 1 && valid) {
                    try { cbalance = sql.getFunds(aName, password); }
                    catch(SQLException e) {
                        e.printStackTrace();
                    }
                    try { balance = Double.parseDouble(cbalance); }
                    catch(Exception e) {
                        e.printStackTrace(); 
                    }
                    if(number > balance && number > 0) {
                        valid = false;
                        System.out.println("\nInsuficient Funds.");
                    }
                }
            }
            return ammount;
        }
}
package com.revature;
//import sql.java;

import java.sql.SQLException;
import java.util.*;

import com.sun.media.jfxmedia.logging.Logger;

import java.io.*;

class account {
        protected String aName, fName, password;
        protected int pLevel, aNum;

        /**
         * Asks the user for a username and password that is checked against the database.
         * @return returns true if the loggin worked
         */
        protected boolean login() {
            boolean loggedIn = true;
            account ac = new account();
            System.out.println("\nEnter Username");
            aName = getString();
            System.out.println("\nEnter Password");
            password = getString();

            System.out.println("\nConnecting:");
            
            if(!sql.isConnected())
                sql.connect();
            try { ac = sql.login(aName, password); }
            catch(SQLException e) {
                e.printStackTrace();
            }
            aName = ac.aName;
            password = ac.password;
            fName = ac.fName;
            pLevel = ac.pLevel;
            aNum = ac.aNum;
            if(aName == null)
                loggedIn = false;
            else
                sql.logger.info(aName + " Logged In");
            return loggedIn;
        }
        /**
         * Asks the user for a number. if the enntered ammount is valid and not exit, the 
         * sql deposit() is called to enter the money into th esers account.
         */
        protected void depositeFunds() {
            String ammount = getAmmount(0);
            if(!ammount.equals("exit")) {
                if(!sql.isConnected())
                    sql.connect();
                try { 
                    sql.deposit(ammount, aNum);
                    System.out.println("New Balance is");
                    sql.getFunds(aName, password, 0);
                }
                catch(SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        /**
         * Asks the user for a number, if a valid number was entered and not exit. Then the sql withdraw
         * function is called and the funds are removed from the account. if exit was selected, then the function
         * closes without doing anything.
         */
        protected void withdrawFunds() { 
            String ammount = getAmmount(1);
            if(!ammount.equals("exit")) {
                if(!sql.isConnected())
                    sql.connect();
                try {
                    sql.withdraw(ammount, aNum);
                    System.out.println("New Balance is");
                    sql.getFunds(aName, password, 0);
                }
                catch(SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        /**
         * Asks for an ammount, if not exit it asks for an account number. the account number is checked and
         * if valid, deposit is called on the passed account while withdraw is called on the users account.
         * if the account was not valid the function ends instead.
         */
        protected void transferFunds() {
            String ammount = getAmmount(1);
            Integer acc = 0;
            boolean missing = true;
            if(!ammount.equals("exit")) {
                System.out.println("Enter account number to tranfer to.");
                acc = getNum();
                if(!sql.isConnected())
                    sql.connect();
                try { 
                    missing = sql.checkColumn("accnum", acc.toString()); 
                    System.out.println(acc.toString() + "\t" + missing);
                }
                catch(SQLException e) {
                    e.printStackTrace();
                }
                if(!missing) {
                    try { 
                        sql.withdraw(ammount, aNum);
                        sql.deposit(ammount, acc);
                        System.out.println("New Balance is");
                        sql.getFunds(aName, password, 0);
                    }
                    catch(SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        /**
         * calls the getFunds() function in the sql class.
         */
        protected void viewFunds() {
            if(!sql.isConnected())
                sql.connect();
            try { 
                sql.getFunds(aName, password, 0);
                banking.Continue();
            }
            catch(SQLException e) {
                e.printStackTrace();
            }
        }

        /**
         * asks the user for a username and email. these two values are checked against the database to see if they are taken.
         * afterwords it asks the user for the other account info and passes it to the createAccount sql function.
         */
        void createAccount() {
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

        /**
         * Asks the user for anothers username. if the username exists the sql merge function is called which stores your request.
         */
        protected void joinAccount() {
            String userName = "";
            boolean joined = true;
            while(joined) {
                try { 
                    System.out.println("Enter User Name of the person you want to join with");
                    userName = getString();
                    joined = sql.checkColumn("uname", userName);
                }
                catch(SQLException e) {
                    e.printStackTrace();
                }
                try {
                    System.out.println("Setting user " + userName + "for merge"); 
                    sql.joinAccount(userName, aName); }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        /**
         * Grabs the next single line string from the user. is currently being used in most class files.
         * @return returns a string
         */
        static String getString() {
         
            Scanner AccOp = new Scanner(System.in);
            String userIn = "";
		    try{userIn = AccOp.nextLine();}
		    catch(InputMismatchException err) {
			System.out.println("Some Error\n");
		    }
            return userIn;
        }

        /** 
         * takes a 0 or 1, 0 just checks that the user enters a valid integer.
         * 1 also check that the valid integer is less than his max account balance.
         */
        String getAmmount(int check) {
            boolean valid = false, cont = true;
            double number = 0, balance = 0;
            String ammount = "", cbalance = "";
            
            while(!valid && cont) {
                valid = true;

                System.out.println("\nEnter ammount");
                ammount = getString();
                if(!ammount.equals("exit")) {
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
                        try { cbalance = sql.getFunds(aName, password, 0); }
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
            }
            return ammount;
        }

        /**
         * grabs and returns a single int. this is getting called alot so i placed the code here.
         * @return returns an int from the user.
         */
        static Integer getNum() {
            Integer num = 0;
            while(num == 0) {
                try { num = Integer.parseInt(getString()); }
                catch(Exception e) {
                    //e.printStackTrace();
                    System.out.println("Must enter a valid int:");
                }
            }
            return num;
        }
}
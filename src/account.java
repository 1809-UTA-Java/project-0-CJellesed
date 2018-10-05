package com.revature;
//import sql.java;

import java.sql.SQLException;
import java.util.*;
import java.io.*;

class account {
        protected String aName, fName, password;
        protected int pLevel;
        protected double cFunds;
        // I think a default constructor should be left out, because we want no empty accounts. Check later.
        protected account() {
            this.aName = "";
            this.password = "";
            this.fName = "";
            this.pLevel = 0;
            this. cFunds = 0.00;
        }

        protected account(String aName, String password, String fName, int pLevel, double cFunds) {
            this.aName = aName;
            this.password = password;
            this.fName = fName;
            this.pLevel = pLevel;
            this. cFunds = cFunds;
        }

        protected void login() {
            System.out.println("\nEnter Username");
            aName = getString();
            System.out.println("\nEnter Password");
            password = getString();

            System.out.println("Connecting:");
            
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
            }
        }
        protected void depositeFunds() {

        }
        protected void withdrawFunds() {};
        protected void transferFunds() {};
        protected void viewFunds() {
            sql.connect();
            try { sql.getFunds(aName, password); }
            catch(SQLException e) {
                e.printStackTrace();
            }
        }

        void createAccount() {
            //Work on the getter and setters here.
            String uname = "", password = "", fname = "", lname = "", email = "";
            boolean free = false;
            while (!free) {
                System.out.println("Enter Username.");
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

            try { sql.createAccount(uname, password, fname, lname, email); }
            catch (SQLException e) {
                e.printStackTrace();
            }
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
}
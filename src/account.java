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
            //String funds = "", name = "", pswrd = "";
            //name = getAName();
            //pswrd = getPassword();
            // System.out.println("\nEnter Username");
            // name = getString();
            // System.out.println("\nEnter Password");
            // pswrd = getString();
            sql.connect();
            try { sql.getFunds(aName, password); }
            catch(SQLException e) {
                e.printStackTrace();
            }
            //return funds;
        }
        String getAName() {
            return aName;
        }
        String getPassword() {
            return password;
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
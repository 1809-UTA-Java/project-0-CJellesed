package com.revature;
//import sql.java;

import java.sql.SQLException;
import java.util.*;
import java.io.*;

class account {
        // I think a default constructor should be left out, because we want no empty accounts. Check later.
        protected account() {
            this.aName = "";
            this.fName = "";
            this.pLevel = 0;
            this. cFunds = 0.00;
        }

        protected account(String aName, String fName, int pLevel, double cFunds) {
            this.aName = aName;
            this.fName = fName;
            this.pLevel = pLevel;
            this. cFunds = cFunds;
        }

        protected void login() {

        }
        protected void depositeFunds() {

        }
        protected void withdrawFunds() {};
        protected void transferFunds() {};
        static protected String viewFunds() {
            String funds = "", name = "", pswrd = "";
            System.out.println("\nEnter Username");
            name = getString();
            System.out.println("\nEnter Password");
            pswrd = getString();
            sql.connect();
            try { sql.getFunds(name, pswrd); }
            catch(SQLException e) {
                e.printStackTrace();
            }
            return funds;
        }

        protected String aName, fName;
        protected int pLevel;
        protected double cFunds;

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
package com.revature;
//import sql.java;

import java.sql.SQLException;

class account {
        // I think a default constructor should be left out, because we want no empty accounts. Check later.
        protected account(String aName, String fName, String lName, int pLevel, double cFunds) {
            this.aName = aName;
            this.fName = fName;
            this.lName = lName;
            this.pLevel = pLevel;
            this. cFunds = cFunds;
        }
        protected void depositeFunds() {

        }
        protected void withdrawFunds() {};
        protected void transferFunds() {};
        static protected String viewFunds() {
            String funds = "";
            sql.connect();
            try { sql.getFunds(); }
            catch(SQLException e) {
                e.printStackTrace();
            }
            return funds;
        }

        protected String aName, fName, lName;
        protected int pLevel;
        protected double cFunds;
}
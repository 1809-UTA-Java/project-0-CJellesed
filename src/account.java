package com.revature;

abstract class account {
        // I think a default constructor should be left out, because we want no empty accounts. Check later.
        protected account(String aName, String fName, String lName, int pLevel, double cFunds) {
            this.aName = aName;
            this.fName = fName;
            this.lName = lName;
            this.pLevel = pLevel;
            this. cFunds = cFunds;
        }
        abstract protected void depositeFunds();
        abstract protected void withdrawFunds();
        abstract protected void transferFunds();

        private String aName, fName, lName;
        private int pLevel;
        private double cFunds;
}
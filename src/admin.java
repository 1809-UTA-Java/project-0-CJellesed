package com.revature;

import java.sql.SQLException;
//import jdk.nashorn.internal.ir.GetSplitState;

import javax.lang.model.util.ElementScanner6;

class admin {

    static void depositeFunds(account ac) {
        String ammount = ac.getAmmount(0);
        Integer acc = 0;
        if(!ammount.equals("exit")) {
            if(!sql.isConnected())
                sql.connect();
            System.out.println("Account Number");
            acc = account.getNum();
            
            try { 
                if(!sql.deposit(ammount, acc))
                    System.out.println("Unable to find account");
            }
            catch(SQLException e) {
                e.printStackTrace();
            }
        }
    }

    static void withdrawFunds(account ac) { 
        String ammount = ac.getAmmount(0);
        if(!ammount.equals("exit")) {
            System.out.println("Enter Account Number");
            Integer aNum = account.getNum();
            if(!sql.isConnected())
                sql.connect();
            try {
                sql.withdraw(ammount, aNum);
            }
            catch(SQLException e) {
                e.printStackTrace();
            }
        }
    }
    static void transferFunds(account ac) {
        String ammount = ac.getAmmount(0);
        Integer accFrom = 0, accTo = 0;
        if(!ammount.equals("exit")) {
            System.out.println("Here: " + ammount);
            System.out.println("Enter account number to tranfer from.");
            accFrom = account.getNum();
            System.out.println("Enter account number to tranfer to.");
            accTo = account.getNum();
            if(!sql.isConnected())
                sql.connect();
            try { 
                sql.withdraw(ammount, accFrom);
                sql.deposit(ammount, accTo);
            }
            catch(SQLException e) {
                e.printStackTrace();
            }
        }
    }
    // Currently can only cancel. Normally I would let admins freely change these values.
    // I can add that in if time permits.
    static void cancelAccount() {
        String user = "";
        System.out.println("Enter Username");
        user = account.getString();

        try { sql.setAccount(user, -1, 0); }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }
}
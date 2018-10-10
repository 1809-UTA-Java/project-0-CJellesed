package com.revature;

import java.sql.SQLException;
//import jdk.nashorn.internal.ir.GetSplitState;

import javax.lang.model.util.ElementScanner6;

class admin {

    /**
     * asks for an account number and ammount to deposit. admins can deposit any positive number
     * to any account.
     */
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

    /**
     * asks for an account number and ammount to withdraw. addmins can withdraw any number and take the balance
     * below 0. At that point the customer owes the bank money.
     * @param ac account class object
     */
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
    /**
     * takes an ammount and two account numbers, the balance can drop below 0.
     * @param ac account class object.
     */
    static void transferFunds(account ac) {
        String ammount = ac.getAmmount(0);
        Integer accFrom = 0, accTo = 0;
        if(!ammount.equals("exit")) {
            //System.out.println("Here: " + ammount);
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
    /**
     * takes a username and cancels that users account.
     */
    static void cancelAccount() {
        String user = "";
        System.out.println("Enter Username");
        user = account.getString();

        try { 
            sql.setAccount(user, -1, 2);
            System.out.println("Account Canceled");
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }
}
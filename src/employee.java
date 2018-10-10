package com.revature;

import java.sql.SQLException;

import jdk.nashorn.internal.ir.GetSplitState;

class employee {
    /**
     * alls the sql get user function. asks the user to either hit enter or enter a username
     */
    static void getUsers() {
        String choice = "";
        System.out.println("\nEnter username to look up or press enter to search all.");
        choice = account.getString();
        if(choice.isEmpty()) {
            try { sql.showUsers("", 1); }
            catch(SQLException e) {
                e.printStackTrace();
            }
        }
        else {
            try { sql.showUsers(choice, 2); }
            catch(SQLException e) {
                e.printStackTrace();
            }
        }

    }
    /**
     * asks for a username and calls the sql getfunds()
     */
    static void getBalance() {
        String user = "";
        System.out.println("Enter Username");
        user = account.getString();
        try { sql.getFunds(user, "", 1); }
        catch(SQLException e) {
            e.printStackTrace();
        }

    }
    /**
     * asks for a username and to enter 1 or 2. then calls the sql set account function.
     */
    static void setAccount() {
        boolean set = false;
        String user = "", choice = "";
        int approve = -1;
        System.out.println("Enter Username");
        user = account.getString();
        while(!set) {
            System.out.println("1 - Approve Account\t2 - Deny Account");
            choice = account.getString();
            System.out.println(choice);
            if(choice.equals("1")) {
                System.out.println("in " + choice);

                set = true;
                approve = 1;
                try { sql.createMoneyAccount(user, 0.0); }
                catch(SQLException e) {
                    e.printStackTrace();
                }
            }
            else if (choice.equals("2")) {
                approve = -1;
                set = true;
            }
        }
        try { sql.setAccount(user, approve, 0); }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * returns all users who requested an account merge.
     */
    static void showMergers() {
        try { sql.showUsers("", 3); }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * currently asks for two users and their account numbers. the funds of each account are merged into the first and both
     * now share account 1.
     */
    static void approveMerge() {
        String userOne = "", userTwo = "";
        Integer UOneAccount = 0, UTwoAccount = 0;
        System.out.println("Enter First Username");
        userOne = account.getString();
        System.out.println("Enter First Account Number");
        UOneAccount = account.getNum();
        System.out.println("Enter Second Username");
        userTwo = account.getString();
        System.out.println("Enter Second Account NUmber");
        UTwoAccount = account.getNum();
        String ammount = "";
        try {  ammount = sql.getFunds(userTwo, "", 1); }
        catch(SQLException e) {
            e.printStackTrace();
        }
        System.out.println(ammount);
        try { sql.withdraw(ammount, UTwoAccount); }
        catch(SQLException e) {
            e.printStackTrace();
        }
        try { sql.deposit(ammount, UOneAccount); }
        catch(SQLException e) {
            e.printStackTrace();
        }
        try {  sql.setAccountNum(userTwo, UOneAccount); }
        catch(SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Merge Done");
    }
}
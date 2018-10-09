package com.revature;

import java.sql.SQLException;

import jdk.nashorn.internal.ir.GetSplitState;

class employee {
    
    static void getUsers() {
        String choice = "";
        System.out.println("\nEnter username to look up or press enter to search all.");
        choice = account.getString();
        if(choice.isEmpty()) {
            try { sql.showUsers("", true); }
            catch(SQLException e) {
                e.printStackTrace();
            }
        }
        else {
            try { sql.showUsers(choice, false); }
            catch(SQLException e) {
                e.printStackTrace();
            }
        }

    }
        
    static void getBalance() {
        String user = "";
        System.out.println("Enter Username");
        user = account.getString();
        try { sql.getFunds(user, "", 1); }
        catch(SQLException e) {
            e.printStackTrace();
        }

    }
        
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
            }
            else if (choice.equals("2")) {
                approve = 2;
                set = true;
            }
        }
        try { sql.setAccount(user, approve, 0); }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }
}
package com.revature;

import java.sql.SQLException;

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
        
    public void gAccBalance() {

    }
        
    public void gPInfo() {
        System.out.println("test");
    }
        
    public void gCApplication() {

    }
}
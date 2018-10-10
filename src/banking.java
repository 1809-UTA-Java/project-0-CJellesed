package com.revature;

import java.util.*;

import javax.lang.model.util.ElementScanner6;

import java.io.*;
//import account.java;

// run with - java -cp ./bin/ com.revature.banking 
// javac -cp .:ojdbc8.jar:log4j-1.2.17.jar:junit-4.10.jar src/* -d bin/
// java -cp bin:ojdbc8.jar:log4j-1.2.17.jar:junit-4.10.jar com.revature.banking

class banking {
    static String choice = "";
    static boolean mainCont = true , loggedIn = false;

    /**
     * has two while loops, the first allows you to close the program, login or create account. logging
     * in takes you into the inner while while logging out takes you back to the first while. The inner while has
     * all the other functionality and displays options based on the login priv level.
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("Welcome to the bank\n");
        account ac = new account();
        while(mainCont) {
            login(ac);
            if(loggedIn)
                runUser(ac);
        }
        if(sql.isConnected())
            sql.disconnect();
    }

    /**
     * displays 3 options anc calls them. if an invalid choice is made you go back to the outer while in main.
     * @param ac account class object.
     */
    static void login(account ac) {
        //boolean mainCont = true;
            System.out.println("\n1 - Login\t2 - Create Account\t3 - Exit");
            choice = getString();
            switch(choice) {
                case "1":   if(ac.login())
                                loggedIn = true;
                            break;
                case "2":   ac.createAccount();
                            break;
                case "3":   System.out.println("Have a nice day!");
                            mainCont = false;
                            break;
                default:    System.out.println("Invalid Input");
            }
    }
    /**
     * main driver for the program. checks the user level and displays options.
     * @param ac account class object.
     */
    static void runUser(account ac) {
        boolean cont = true;
        int level = ac.pLevel;
        while(cont) {
            System.out.println("\n1 - View Balance\t2 - Deposit Funds\t3 - Withdraw Funds");
            System.out.println("4 - Transfer Funds\t5 - Logout\t6 - Apply for Joint Account");
            if(level > 0)
                System.out.println("\nEmployee Controls:\n7 - View Customers\t8 - View Customer Balance\t9 - Approve Account\n14 - Finalize Merge\t15 - Show Merge Requests");
            if(level == 2)
                System.out.println("\nAdmin Controls:\n10 - Deposit Funds\t11 - Withdraw Funds\t12 - Transfer Funds\t13 - Cancel Account");
            System.out.println("\nEnter Selection:");
            choice = getString();
            switch(choice) {
                case "1":   ac.viewFunds();
                            break;
                case "2":   ac.depositeFunds();
                            break;
                case "3":   ac.withdrawFunds();
                            break;
                case "4":   ac.transferFunds();
                            break;
                case "5":   System.out.println("Logging Out");
                            //Expand when accoutns are implemented.        
                            loggedIn = false;
                            cont = false;
                            break;
                case "6":   ac.joinAccount();
                            break;
                case "7":   if(level > 0)
                                employee.getUsers();
                            else
                                System.out.println("Invalid Input");  
                            break;
                case "8":   if(level > 0)
                                employee.getBalance();
                            else
                                System.out.println("Invalid Input");
                            break;
                case "9":   if(level > 0) 
                                employee.setAccount();
                            else
                                System.out.println("Invalid Input");
                            break;
                case "10":  if(level == 2) 
                                admin.depositeFunds(ac);
                            else
                                System.out.println("Invalid Input");
                            break;
                case "11":  if(level == 2)
                                admin.withdrawFunds(ac);
                            break;
                case "12":  if(level == 2) 
                                admin.transferFunds(ac);
                            else
                                System.out.println("Invalid Input");
                            break;
                case "13":  if(level == 2) 
                                admin.cancelAccount();
                            else
                                System.out.println("Invalid Input");
                            break;
                case "14":  if(level > 0)
                                employee.approveMerge();
                                break;
                case "15":  if(level > 0)
                                employee.showMergers();
                                break;
                default: System.out.println("Invalid Input");
            }
        }
    }
    /**
     * Grabs and returns a string from the scanner.
     * @return a string
     */
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

    /**
     * asks the user for input, used to pasuse the loop when needed.
     */
    static void Continue() {
        System.out.println("\nPress any key to continue.");
        try {
            System.in.read();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
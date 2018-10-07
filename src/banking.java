package com.revature;

import java.util.*;
import java.io.*;
//import account.java;

// run with - java -cp ./bin/ com.revature.banking 
// javac -cp .:ojdbc8.jar src/* -d bin/
// java -cp bin:ojdbc8.jar com.revature.banking

class banking {
    static String choice = "";
    static boolean mainCont = true , loggedIn = false;
    public static void main(String[] args) {
        System.out.println("Welcome to the bank\n");
        account ac = new account();
        while(mainCont) {
            login(ac);
            if(loggedIn)
                runUser(ac);
        }
        
        
    }

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
    static void runUser(account ac) {
        boolean cont = true;
        //String choice = "";
        while(cont) {
            System.out.println("\n1 - View Balance\t2 - Deposit Funds\t3 - Withdraw Funds");
            System.out.println("4 - Transfer Funds\t5 - Logout\t6 - Apply for Joint Account");
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
                default: System.out.println("Invalid Input");
            }
        }
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

    static void Continue() {
        System.out.println("\nPress any key to continue.");
        try {
            System.in.read();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
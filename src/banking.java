package com.revature;

import java.util.*;
import java.io.*;
//import account.java;

// run with - java -cp ./bin/ com.revature.banking 
// javac -cp .:ojdbc8.jar src/* -d bin/
// java -cp bin:ojdbc8.jar com.revature.banking

class banking {
    public static void main(String[] args) {
        System.out.println("Welcome to the bank\n");
        runUser();
        
    }

    static void runUser() {
        boolean cont = true;
        String choice = "";
        while(cont) {
            System.out.println("\n1 - View Balance\t2 - Withdraw Funds\t3 - Transfer Funds");
            System.out.println("4 - Logout\t5 - Apply for Joint Account");
            choice = getString();
            switch(choice) {
                case "1":   account.viewFunds();
                            break;
                case "2":   System.out.println("Withdraw Funds");
                            break;
                case "3":   System.out.println("Trandsfer Funds");
                            break;
                case "4":   System.out.println("Logging Out");
                            //Expand when accoutns are implemented.        
                    cont = false;
                            break;
                case "5":   System.out.println("Apply for Joint Account");
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
        System.out.println("Press any key to continue.");
        try {
            System.in.read();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
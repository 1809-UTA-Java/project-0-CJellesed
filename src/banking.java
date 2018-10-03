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
        System.out.println("1 - log in.\t2 - Create account\n");
        String temp = getString();
        account.viewFunds();
        if(temp == "1") {
            //account.viewFunds();
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
}
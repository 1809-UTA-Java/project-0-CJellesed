package com.revature;

import java.util.*;
import java.io.*;

class banking {
    public static void main(String[] args) {
        System.out.println("Welcome to the bank\n");
        System.out.println("1 - log in.\t2 - Create account\n");
        System.out.println(getString());
        System.out.println("Take 2");
        System.out.println(getString());
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
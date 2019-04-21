package com.lavrovivan;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException {
        File file = new File(System.getProperty("user.dir") + File.separator + "myPhoneBook.txt");

        // add name to the phone book
//        PhoneBook.createName(file);

        // show the whole phone book
//        PhoneBook.printPhoneBook(file);

        if (false) { // test search by last name
            Search searchObj = new Search(file);
            String lastName;

            System.out.println("search by name. " +
                    "Enter 'stop' to end.");
            do {
                lastName = searchObj.getSelection();

                if (!searchObj.searchFor(lastName))
                    System.out.println("last name not found.\n");

            } while (lastName.compareTo("stop") != 0);
        }

        if (false) {
            // test search by phone number
            Search searchObj = new Search(file);
            String phNum;

            System.out.println("search by phone number. " +
                    "Enter 'stop' to end.");
            do {
                phNum = searchObj.getPhoneNumberToSearch();

                if (!searchObj.searchByNumber(phNum))
                    System.out.println("phone number not found.\n");

            } while (phNum.compareTo("stop") != 0);
        }
    }
}

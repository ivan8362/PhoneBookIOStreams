package com.lavrovivan;

import java.io.*;

class Search {
    File file; //  phone book file

    Search(File file) {
        this.file = file;
    }

    // Display numbers found.
    boolean searchFor(String what) {
        int ch;
        String wholeLine;
        String lastName;
        int spaceIndex = -1;

        // Open the help file.
        try (BufferedReader bufferedRdr =
                     new BufferedReader(new FileReader(file)))
        {
            do {
                // read characters until a # is found
                ch = bufferedRdr.read();

                // now, see if last names match
                if(ch == '#') {
                    wholeLine = bufferedRdr.readLine();

                    // Find the First space.
                    spaceIndex = wholeLine.indexOf(" ");

                    lastName = wholeLine.substring(0, spaceIndex);

                    if(what.compareTo(lastName) == 0) { // found last name
                            if(wholeLine != null && (wholeLine.compareTo("") != 0)) System.out.println(wholeLine);
                        return true;
                    }
                }
            } while(ch != -1);
        }
        catch(IOException exc) {
            System.out.println("Error accessing help file.");
            return false;
        }
        return false; // topic not found
    }

    // Get a last name.
    String getSelection() {
        String lastName = "";

        BufferedReader br = new BufferedReader(
                new InputStreamReader(System.in));

        System.out.print("Enter last name: ");
        try {
            lastName = br.readLine();
        }
        catch(IOException exc) {
            System.out.println("Error reading console.");
        }

        return lastName;
    }

    // Get a phone number to search.
    String getPhoneNumberToSearch() {
        String phoneNumber = "";

        BufferedReader br = new BufferedReader(
                new InputStreamReader(System.in));

        System.out.print("Enter phone number: ");
        try {
            phoneNumber = br.readLine();
        }
        catch(IOException exc) {
            System.out.println("Error reading console.");
        }
        return phoneNumber;
    }

    boolean searchByNumber(String userInput) {
        String wholeLine = "";
        String[] split;


        // Open the file.
        try (BufferedReader bufferedRdr =
                     new BufferedReader(new FileReader(file)))
        {
            do { // for one row in the file
                // read line
                wholeLine = bufferedRdr.readLine();
                if (wholeLine == null) return false;
                // split by space.
                split = wholeLine.split(" ");

                for (String aSplit : split) {
                    if (userInput.equals(aSplit)) { // found number
                        if (wholeLine != null && (wholeLine.compareTo("") != 0)) {
                            System.out.println(wholeLine);
                        }
                        return true;
                    }
                }
            } while(!wholeLine.equals(null));
        }
        catch(IOException exc) {
            System.out.println("Error accessing help file.");
            return false;
        }
        return false; // num not found
    }
}

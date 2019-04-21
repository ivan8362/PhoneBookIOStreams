package com.lavrovivan;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class PhoneBook {
    public static void createName(File fileName) {
        System.out.println("type First Name, Last name (mandatory), nick name, home, office, cell and fax number, " +
                "email, year of birth \n(only one record can be inserted for one run, " +
                "type 'end' after the input). One of the numbers is mandatory." +
                "you will see in the file before last name # sign - to implement search feature");

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String string = "";
        FileWriter fileWriter = null;
        ArrayList<Long> allPhoneNumbers = new ArrayList<>();
        String regex = "[0-9]+";

        try {
            fileWriter = new FileWriter(fileName, true);

            do { // for one record
                string = br.readLine();

                String subString[];

                // split string by space character
                subString = splitBySpace(string);

                // find how many numbers user inserted
                int numberQuantity = 0;
                int firstNumberIndex = -1;

                for (int i = 0; i < subString.length; i++) {
                    if(subString[i].matches(regex) && subString[i].length() != 4) {
                        if (firstNumberIndex == -1) {
                            firstNumberIndex = i;
                        }

                        numberQuantity++;
                    }
                }

                allPhoneNumbers = getPhoneNumbers(fileName); // get all numbers from file to the array

                // check for a duplicate number in the current phone book

                if (string.compareTo("end") != 0 &&
                        checkIfDuplicate(allPhoneNumbers, subString, firstNumberIndex, numberQuantity)) {
                    System.out.println("There is a duplicate number in the input. please, try again");
                    break;
                } else System.out.println("no duplicates");

                // check if e-mail is correct
                if (checkIfMailOk(subString, firstNumberIndex, numberQuantity, regex)){
                    System.out.println("email is OK or there is no email in input.");
                } else break;

                // Get number of characters before the last name
                int hashSignPosition = getHashSighPosition(subString, firstNumberIndex);

                String newString = "";
                if (string.compareTo("end") != 0) {

                    // Add # before each last name just to use it for search feature.
                    newString = string.substring(0, hashSignPosition) + "#" +
                            string.substring(hashSignPosition);

                    // Add new phone numbers to the array.
                    allPhoneNumbers = addToAllNumbers(allPhoneNumbers, subString, firstNumberIndex, numberQuantity);
                    System.out.println(allPhoneNumbers);
                } else break;

                newString = newString + "\r\n";
                fileWriter.append(newString);
            } while (string.compareTo("end") != 0);
        } catch (IOException e) {
            System.out.println("io exception: " + e);
        }

        try {
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("io exception: " + e);
        }
    }

    static ArrayList getPhoneNumbers(File file) {
        ArrayList<Long> longs = new ArrayList<>();

        try {
            Scanner scanner = new Scanner(file);
            // fill arrayList
            while (scanner.hasNext()) {
                if (scanner.hasNextLong()) {
                    longs.add(scanner.nextLong());
                } else {
                    scanner.next();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // udalit' vse elementy massiva, poluchenniye iz kolonki goda rozhdeniya
        for (int i = 0; i < longs.size(); i++) {
            if (longs.get(i) < 9999) {
                longs.remove(i);
            }
        }

        System.out.println(longs);
        return longs;
    }

    static boolean checkIfDuplicate(ArrayList<Long> allPhoneNumbers, String[] subString,
                                    int firstNumberIndex, int numberQuantity) {
        if (firstNumberIndex != -1 && numberQuantity != 0) {
            for (int i = firstNumberIndex; i < numberQuantity + firstNumberIndex; i++) {
                for (int j = 0; j < allPhoneNumbers.size(); j++) {
                    if (subString[i].equals(allPhoneNumbers.get(j).toString())) {
                        return true;
                    }
                }
            }
        } else {
            System.out.println("please insert at least one number");
            return true;
        }

        return false;
    }

    static ArrayList<Long> addToAllNumbers(ArrayList<Long> allPhoneNumbers, String[] subString,
                                int firstNumberIndex, int numberQuantity) {

        // ТОЛЬКО ДЛЯ ТЕСТИРОВАНИЯ, НЕ СМОТРИТЕ СЮДА. show the input
        /*System.out.println("allPhoneNumbers " + allPhoneNumbers);
        System.out.print("subst: ");
        for (String string : subString) {
            System.out.println(string);
        }
        System.out.println("1st num ind " + firstNumberIndex);
        System.out.println("num qua " + numberQuantity);*/

        if (firstNumberIndex != -1 && numberQuantity != 0) {
            for (int i = firstNumberIndex; i < numberQuantity + firstNumberIndex; i++) {
                allPhoneNumbers.add(Long.parseLong(subString[i]));
            }
        } else {
            System.out.println("please insert at least one number");
        }
        return allPhoneNumbers;
    }

    static void printPhoneBook(File file) {
        int i;
        FileInputStream fin = null;

        // The following code opens a file, reads characters until EOF
        // is encountered, and then closes the file via a finally block.
        try {
            fin = new FileInputStream(file);

            do {
                i = fin.read();
                if(i != -1) System.out.print((char) i);
            } while(i != -1);

        } catch(FileNotFoundException exc) {
            System.out.println("File Not Found.");
        } catch(IOException exc) {
            System.out.println("An I/O Error Occurred");
        } finally {
            // Close file in all cases.
            try {
                if(fin != null) fin.close();
            } catch(IOException exc) {
                System.out.println("Error Closing File");
            }
        }
    }

    static void searchByLastName(File file) {
        // Get info from file.
        int i;
        FileInputStream fin = null;

        // The following code opens a file, reads characters until EOF
        // is encountered, and then closes the file via a finally block.
        try {
            fin = new FileInputStream(file);

            do {
                i = fin.read();
                if(i != -1) System.out.print((char) i);
            } while(i != -1);

        } catch(FileNotFoundException exc) {
            System.out.println("File Not Found.");
        } catch(IOException exc) {
            System.out.println("An I/O Error Occurred");
        } finally {
            // Close file in all cases.
            try {
                if(fin != null) fin.close();
            } catch(IOException exc) {
                System.out.println("Error Closing File");
            }
        }
    }

    static int getHashSighPosition(String[] subString, int firstNumberIndex) {
        int hashSignPosition = 0;
        switch (firstNumberIndex) {
            case 1:
                break;
            case 2:
            case 3:
                hashSignPosition = subString[0].length() + 1;
                break;
        }

        return hashSignPosition;
    }

    static String[] splitBySpace(String string) {
        return string.split(" ");
    }

    static boolean checkIfMailOk(String[] substring, int firstNumberIndex, int numberQuantity, String regex) {
        // check if email exists in input
        int emailIndex = checkIfMailInserted(substring, firstNumberIndex, numberQuantity, regex);

        if (emailIndex == -1) {
            return true;
        } else {
            String email = substring[emailIndex];

            // regex expression to check if email is valid
            String mailRegex = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

            if (email.matches(mailRegex)) return true;
            else return false;
        }
    }

    static int checkIfMailInserted(String[] substring, int firstNumberIndex, int numberQuantity, String regex) {
        // returns index number of the email string in user input array of strings
        switch (substring.length - (firstNumberIndex + numberQuantity)) {
            case 0:
                // no email inserted
                return -1;
            case 1: // user inserted email or year of birth but not both values
                // check if year of birth exists in the input
                if (substring[substring.length - 1].matches(regex) && substring[substring.length - 1].length() == 4) {
                    return -1;
                } else {
                    return firstNumberIndex + numberQuantity;
                }
            case 2:
                return firstNumberIndex + numberQuantity;
            default:
                return -1;
        }
    }
}

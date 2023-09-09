import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.util.Random;
import java.util.Scanner;

public class PersonalAccount {
    public static void CreateAccount() {
        Scanner userDetails = new Scanner(System.in);
        //asking user details
        System.out.println("Please provide customer's name: ");
        String name = userDetails.nextLine();

        //ensuring form of date is valid.
        LocalDate dateOfBirth = null;
        boolean validDateFormat = false;
        //random generated numbers for each.

        String accountNumber = generateRandomAccountNumber();
        String sortCode = generateRandomSortCode();
        String pin = generateRandomPIN();

//while loop for date format validation
        while (!validDateFormat) {
            System.out.println("Please provide customer's date of birth in format YYYY-MM-DD: ");
            DateTimeFormatter dateformat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String birthInput = userDetails.nextLine();

            try {
                dateOfBirth = LocalDate.parse(birthInput, dateformat);
                System.out.println("Date of Birth: " + dateOfBirth);
                validDateFormat = true;
            } catch (java.time.format.DateTimeParseException e) {
                System.out.println("Invalid date format. Please use yyyy-MM-dd format.");
            }
        }

        //rest of the details asked from user
        System.out.println("Please confirm photo ID proof of customer (Yes/No) : ");
        boolean photoID = userDetails.nextLine().equalsIgnoreCase("Yes");
        System.out.println("Please confirm proof of address of customer (Yes/No) : ");
        boolean addressID = userDetails.nextLine().equalsIgnoreCase("Yes");
        System.out.println("Confirm minimum deposit of £1 (Yes/No) : ");
        boolean minimumDeposit = userDetails.nextLine().equalsIgnoreCase("Yes");
        System.out.println("Create a username for customer: ");
        String username = userDetails.nextLine();

//details of user saved in customer information page
        String customerInfoCreate = "Username: " + username + "\n" +
                "Name: " + name + "\n" +
                "Date of Birth: " + dateOfBirth + "\n" +
                "Photo ID: " + (photoID ? "Verified" : "Not Provided") + "\n" +
                "Address Proof: " + (addressID ? "Verified" : "Not Provided") + "\n" +
                "Minimum Deposit: " + (minimumDeposit ? "Confirmed" : "Not Confirmed") + "\n" +
                "Account Number: " + accountNumber + "\n" +
                "Sort Code: " + sortCode + "\n" +
                "PIN: " + pin;

//select details of user saved in access file which is viewed and accessed later.
        String customerInfoAccess = "Username: " + username + "\n" +
                "Name: " + name + "\n" +
                "Account Number: " + accountNumber + "\n" +
                "Sort Code: " + sortCode + "\n";
        // "PIN: " + pin;

        // Write customer information to a file named after their username
        String path = "C:\\Users\\user\\Desktop\\NovusGroupProject\\BankingApplication\\CreateAccountFiles\\";
        String fileName = path + username + ".txt";
        try {
            FileWriter fileWriter = new FileWriter(fileName);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.println(customerInfoCreate);
            printWriter.close();
            System.out.println("Customer information saved to '" + fileName + "'.");
        } catch (IOException e) {
            System.out.println("Error occurred while saving customer information.");
        }
        //Customer information for access file named after username with same sort code and account number
        String path2 = "C:\\Users\\user\\Desktop\\NovusGroupProject\\BankingApplication\\AccessAccountFiles\\";
        String fileName2 = path2 + username + ".txt";
        try {
            FileWriter fileWriter = new FileWriter(fileName2);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.println(customerInfoAccess);
            printWriter.close();
            System.out.println("Customer information saved to '" + fileName2 + "'.");
        } catch (IOException e) {
            System.out.println("Error occurred while saving customer information.");
        }
    }
//code for randomly generated account, sort code and pin numbers
    private static String generateRandomAccountNumber() {
        Random random = new Random();
        StringBuilder accountNumber = new StringBuilder("A");
        for (int i = 0; i < 9; i++) {
            accountNumber.append(random.nextInt(10));
        }
        return accountNumber.toString();
    }

    private static String generateRandomSortCode() {
        Random random = new Random();
        StringBuilder sortCode = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            sortCode.append(random.nextInt(10));
            if (i == 1 || i == 3) {
                sortCode.append("-");
            }
        }
        return sortCode.toString();
    }

    private static String generateRandomPIN() {
        Random random = new Random();
        StringBuilder pin = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            pin.append(random.nextInt(10));
        }
        return pin.toString();
    }
}









import java.io.*;
import java.util.*;

public class AccessAccount {
    private static Map<String, List<UserDetails>> userDetailsMap = new HashMap<>();

    public static void access(Scanner scanner) {
        String username = UserAuthentication.getEnteredUsername();
        // VIEW BALANCE, WITHDRAW, TRANSFER, DEPOSIT, VIEW ACCOUNTS
        // GET USERNAME FOR MULTIPLE ACCOUNTS
        System.out.println("Please also provide account number");
        String accountNumberInput = scanner.nextLine(); // Read it as a string
        int accountNumber;
        try {
            accountNumber = Integer.parseInt(accountNumberInput);
        } catch (NumberFormatException e) {
            System.out.println("Invalid account number format.");
            return; // Exit the method or handle the error appropriately
        }

        System.out.println("Please provide sort code. In the format (xx-xx-xx)");
        String sortCode = scanner.nextLine();

        System.out.println(accountNumber + " " + sortCode);
        System.out.println("Please specify what you would like to do.\n1.View Balance\n2.Withdraw\n3.Transfer\n4.Deposit\n5.View Accounts");
        int accesschoice = scanner.nextInt();
        loadUserDetailsFromFile("src/accounts.txt");
        switch (accesschoice) {
            case 1:
                if (checkAccountAccess(username, accountNumber, sortCode)) {
                    String balance = getBalance(username, accountNumber);
                    System.out.println(balance);
                } else {
                    System.out.println("Access Denied");
                }
                break;
            case 2:
                if (checkAccountAccess(username, accountNumber, sortCode)) {
                    // Implement withdraw logic here
                    Integer numbalance = null;
                    String balance = getBalance(username, accountNumber);
                    try {
                        numbalance = Integer.valueOf(balance);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }

                    System.out.println("Please enter amount to withdraw");
                    int amount = scanner.nextInt();
                    System.out.println(balance);
                    if (numbalance > amount) {
                        numbalance = numbalance - amount;
                        // Update the balance in the file
                        updateBalanceInFile("src/accounts.txt", username, accountNumber, String.valueOf(numbalance));

                        // Close and reopen the file before viewing
                        loadUserDetailsFromFile("src/accounts.txt");

                        // Display the updated balance
                        String updatedBalance = getBalance(username, accountNumber);
                       // balance = getBalance(username, accountNumber);
                        System.out.println("Updated Balance: " + updatedBalance);
                    } else {
                        System.out.println("Transaction Denied");
                    }
                }
                break;
            case 3:
                if (checkAccountAccess(username, accountNumber, sortCode)) {
                    // Implement transfer logic here
                }
                break;
            case 4:
                // Implement deposit logic here
                break;
            case 5:
                //printUserDetails();
                break;
        }
    }

    private static void loadUserDetailsFromFile(String fileName) {
        try {
            Scanner fileScanner = new Scanner(new File(fileName));
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 10) {
                    String username = parts[0].trim();
                    String accountNumber = parts[6].trim();
                    String sortCode = parts[7].trim();
                    String balance = parts[9].trim();
                    UserDetails userDetails = new UserDetails(username, accountNumber, sortCode, balance);

                    // Check if the username already exists in the map
                    if (userDetailsMap.containsKey(username)) {
                        userDetailsMap.get(username).add(userDetails);
                    } else {
                        List<UserDetails> accountList = new ArrayList<>();
                        accountList.add(userDetails);
                        userDetailsMap.put(username, accountList);
                    }
                }
            }
            fileScanner.close();
        } catch (FileNotFoundException e) {
            System.err.println("Error: " + e.getMessage());
            // Handle the file not found error here
        }
    }

    private static boolean checkAccountAccess(String username, int accountNumber, String sortCode) {
        List<UserDetails> userAccounts = userDetailsMap.get(username);
        if (userAccounts != null) {
            for (UserDetails userDetails : userAccounts) {
                if (userDetails.getAccountNumber().equals(String.valueOf(accountNumber)) &&
                        userDetails.getSortCode().equals(sortCode)) {
                    return true;
                }
            }
        }
        System.out.println("Access denied.");
        return false;
    }

    // Define a class to hold user details
    private static class UserDetails {
        private String username;
        private String accountNumber;
        private String sortCode;
        private String balance;

        public UserDetails(String username, String accountNumber, String sortCode, String balance) {
            this.username = username;
            this.accountNumber = accountNumber;
            this.sortCode = sortCode;
            this.balance = balance;
        }
        public void setBalance(String newBalance) {
            this.balance = String.valueOf(Integer.parseInt(newBalance));
        }

        // Getter methods for user details
        public String getUsername() {
            return username;
        }

        public String getAccountNumber() {
            return accountNumber;
        }

        public String getSortCode() {
            return sortCode;
        }

        public String getBalance() {
            return balance;
        }

    }




    private static String getBalance(String username, int accountNumber) {
        List<UserDetails> userAccounts = userDetailsMap.get(username);
        if (userAccounts != null) {
            for (UserDetails userDetails : userAccounts) {
                if (userDetails.getAccountNumber().equals(String.valueOf(accountNumber))) {
                    return userDetails.getBalance();
                }
            }
        }
        return "Account not found.";
    }


    private static void updateBalanceInFile(String fileName, String username, int accountNumber, String newBalance) {
        List<String> updatedLines = new ArrayList<>();

        try {
            File file = new File(fileName);
            BufferedReader reader = new BufferedReader(new FileReader(file));

            String line;
            boolean found = false; // To track if the account was found for updating

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 10) {
                    String fileUsername = parts[0].trim();
                    String fileAccountNumber = parts[6].trim();

                    if (fileUsername.equals(username) && fileAccountNumber.equals(String.valueOf(accountNumber))) {
                        // Update the balance in the line
                        parts[9] = newBalance;
                        found = true;
                    }
                    updatedLines.add(String.join(",", parts));
                } else {
                    // Add lines without account information unchanged
                    updatedLines.add(line);
                }
            }

            reader.close();

            // If the account was found, write the updated content back to the file
            if (found) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                    for (String updatedLine : updatedLines) {
                        writer.write(updatedLine);
                        writer.newLine(); // Add a newline character after each line
                    }
                }
                System.out.println("Balance updated successfully.");
            } else {
                System.out.println("Account not found.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


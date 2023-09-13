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
        System.out.println("Please specify what you would like to do.\n1.View Balance\n2.Withdraw\n3.Transfer\n4.Deposit");
        int accessChoice = scanner.nextInt();
        loadUserDetailsFromFile("src/accounts.txt");
        switch (accessChoice) {
            case 1:
                if (checkAccountAccess(username, accountNumber, sortCode)) {
                    String balance = getBalance(username, accountNumber, sortCode);
                    System.out.println(balance);
                } else {
                    System.out.println("Access Denied");
                }
                break;
            case 2:
                if (checkAccountAccess(username, accountNumber, sortCode)) {
                    // Implement withdraw logic here
                    Integer numBalance = null;
                    String balance = getBalance(username, accountNumber, sortCode);
                    try {
                        numBalance = Integer.valueOf(balance);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }

                    System.out.println("Please enter amount to withdraw");
                    int amount = scanner.nextInt();

                    if (numBalance > amount) {
                        numBalance = numBalance - amount;
                        // Update the balance in the file
                        updateBalanceInFile("src/accounts.txt" ,username, accountNumber, String.valueOf(numBalance));

                        // Close and reopen the file before viewing
                        loadUserDetailsFromFile("src/accounts.txt");

                        // Display the updated balance
                        String updatedBalance = getBalance(username, accountNumber,sortCode);
                        // balance = getBalance(username, accountNumber);
                        System.out.println("Updated Balance: " + updatedBalance);
                    } else {
                        System.out.println("Transaction Denied");
                    }
                }
                break;

            case 3: // Transfer

                System.out.println("Please enter the account number for the account you want to transfer to:");
                int transferAccountNumber = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                System.out.println("Please enter the sort code for account you want to transfer to: ");
                String transferSortCode = scanner.nextLine();

                System.out.println("Please enter the username for the account you want to transfer to: ");
                String transferUsername = scanner.nextLine();

                System.out.println("Please enter the amount to transfer:");
                int transferAmount = scanner.nextInt();
                scanner.nextLine();

                transferFunds(username, accountNumber, sortCode, transferUsername, transferAccountNumber, transferAmount, transferSortCode);
                break;


            case 4: //deposit
                if (checkAccountAccess(username, accountNumber, sortCode)) {
                    System.out.println("Please enter the amount to deposit:");
                    int depositAmount = scanner.nextInt();

                    if (depositAmount > 0) {
                        // Deposit the amount and update the balance in the file
                        depositToAccount(username, accountNumber, depositAmount, sortCode);
                    } else {
                        System.out.println("Invalid deposit amount. Amount must be greater than 0.");
                    }
                } else {
                    System.out.println("Access Denied");
                }

                UserDetails userAccount = findUser(username, accountNumber, sortCode);
                if (userAccount != null) {
                    // Check and apply charges or interest here
                    Update.chargeBusinessAccount(userAccount);
                    Update.applyISAInterest(userAccount);

                }

                break;
            default:
                System.out.println("Invalid Output");
                break;
        }
    }



    //use of findUser incorporated with UserDetails so verify account number, sortcode and username
    //using this in the transferFunds method to verify source and target accounts
    public static UserDetails findUser(String username, int accountNumber, String sortCode) {
        for (List<UserDetails> userAccounts : userDetailsMap.values()) {
            for (UserDetails userDetails : userAccounts) {
                if (userDetails.getUsername().equals(username)
                        && userDetails.getAccountNumber().equals(String.valueOf(accountNumber))
                        && userDetails.getSortCode().equals(sortCode)) {
                    return userDetails; // Found a matching user
                }
            }
        }
        return null; // User not found
    }



    //method for transfering funds between accounts
    //transferFunds(username, accountNumber, sortCode, transferUsername, transferAccountNumber, transferAmount, transferSortCode);
    public static void transferFunds(String sourceUsername, int sourceAccountNumber, String sourceSortCode, String targetUsername, int targetAccountNumber, int transferAmount, String targetSortCode) {
        // Check if source and target users exist in userDetailsMap
        System.out.println(sourceUsername+ " "+sourceAccountNumber+ " "+sourceSortCode);
        System.out.println(targetUsername+ " "+ targetAccountNumber+ " "+targetSortCode);
        UserDetails sourceUser = findUser(sourceUsername, sourceAccountNumber, sourceSortCode);
        UserDetails targetUser = findUser(targetUsername, targetAccountNumber, targetSortCode);
        System.out.println(sourceUsername+ " "+sourceAccountNumber+ " "+sourceSortCode);
        System.out.println(targetUsername+ " "+ targetAccountNumber+ " "+targetSortCode);
        if (sourceUser == null || targetUser == null) {
            System.out.println("Source or target user account not found.");
            return;
        }

        // Check if source and target users also exist in the file
        if (!checkAccountAccess(sourceUsername, sourceAccountNumber, sourceSortCode) ||
                !checkAccountAccess(targetUsername, targetAccountNumber, targetSortCode)) {
            System.out.println("Source or target user account not found in the file.");
            return;
        }

        // Converts balances to integers for calculations
        int sourceBalance = Integer.parseInt(sourceUser.getBalance());
        int targetBalance = Integer.parseInt(targetUser.getBalance());

        // Checks if the source account has sufficient balance
        if (sourceBalance >= transferAmount) {
            // Deduct the amount from the source account
            sourceBalance -= transferAmount;
            sourceUser.setBalance(String.valueOf(sourceBalance));

            // Add the transferred amount to the target account
            targetBalance += transferAmount;
            targetUser.setBalance(String.valueOf(targetBalance));

            // Update balances in the file
            updateBalanceInFile("src/accounts.txt", sourceUsername, sourceAccountNumber, String.valueOf(sourceBalance));
            updateBalanceInFile("src/accounts.txt", targetUsername, targetAccountNumber, String.valueOf(targetBalance));

            System.out.println("Transfer of £" + transferAmount + " successful.");
            System.out.println("Updated Source Balance: £" + sourceBalance);
            //System.out.println("Updated Target Balance: £" + targetBalance);
        } else {
            System.out.println("Insufficient balance in the source account.");
        }
    }



    //method for depositing funds to account
    public static void depositToAccount(String username, int accountNumber, int depositAmount, String sortCode) {
        try{String balance = getBalance(username, accountNumber,sortCode);
            int currentBalance = Integer.parseInt(balance);

            // Calculate the new balance after the deposit
            int newBalance = currentBalance + depositAmount;

            // Update the balance in the file
            updateBalanceInFile("src/accounts.txt" , username, accountNumber, String.valueOf(newBalance));

            // Display the updated balance
            System.out.println("Deposit of £" + depositAmount + " successful.");
            System.out.println("Updated Balance: £" + newBalance);
        }catch (NumberFormatException e){
            System.out.println("Error");
        }


    }


    public static void loadUserDetailsFromFile(String fileName) {
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
                    String accountType = parts[4].trim(); // Account type is at index 4
                    UserDetails userDetails = new UserDetails(username, accountNumber, sortCode, balance, accountType);

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


    protected static boolean checkAccountAccess(String username, int accountNumber, String sortCode) {
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
    protected static class UserDetails {
        private String username;
        private String accountNumber;
        private String sortCode;
        private String balance;
        private String accountType;

        //added accountType to be able to check which account to update and deposit into
        public UserDetails(String username, String accountNumber, String sortCode, String balance, String accountType) {
            this.username = username;
            this.accountNumber = accountNumber;
            this.sortCode = sortCode;
            this.balance = balance;
            this.accountType = accountType;
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

        public String getAccountType() {
            return accountType;
        }


    }




    protected static String getBalance(String username, int accountNumber, String sortCode) {
        List<UserDetails> userAccounts = userDetailsMap.get(username);
        if (userAccounts != null) {
            for (UserDetails userDetails : userAccounts) {
                if (userDetails.getAccountNumber().equals(String.valueOf(accountNumber))) {
                    String balance = userDetails.getBalance();
                    String _sortCode = userDetails.getSortCode();
                    if (_sortCode.equals("05-09-19") && Integer.parseInt(balance) >= 20000) {
                        System.out.println("You have reached your limit..");
                        break;
                    } else if (Integer.parseInt(balance) <= 0) {
                        System.out.println("You are currently in red and charges will be applied");
                        return userDetails.getBalance();
                    } else {
                        return userDetails.getBalance();
                    }
                }
            }
        }
        return "Account not found or Limit Reached";
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

                        // Update the balance in memory as well
                        updateUserBalance(username, accountNumber, newBalance);
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

    private static void updateUserBalance(String username, int accountNumber, String newBalance) {
        List<UserDetails> userAccounts = userDetailsMap.get(username);
        if (userAccounts != null) {
            for (UserDetails userDetails : userAccounts) {
                if (userDetails.getAccountNumber().equals(String.valueOf(accountNumber))) {
                    userDetails.setBalance(newBalance);
                    return;
                }
            }
        }
    }
}
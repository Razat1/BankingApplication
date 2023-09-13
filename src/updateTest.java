import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class updateTest {
    public static void main(String[] args) {
        // Create and populate the user database
        Map<String, AccessAccount.UserDetails> userDatabase = loadUserDetailsFromFile("src/accounts.txt");

        System.out.println("What is accountType? \n1.Personal\n2.ISA\n3.Business");
        Scanner access = new Scanner(System.in);
        int accessChoice = access.nextInt();
        access.nextLine(); // Consume the newline character

        System.out.println("What is the username? : ");
        String username = access.nextLine();

        AccessAccount.UserDetails userDetails = userDatabase.get(username);

        if (userDetails != null) {
            String userAccountType = userDetails.getAccountType().trim();
            if (accessChoice == 1 && userDetails.getAccountType().equalsIgnoreCase("Personal")) {
                System.out.println("None");
            } else if (accessChoice == 2 && userDetails.getAccountType().equalsIgnoreCase("ISA")) {
                // Check if it's April
                LocalDate customDate = LocalDate.of(2023, Month.APRIL, 1);
                if (customDate.getMonth() == Month.APRIL) {
                    // Apply ISA interest if it's April
                    Update.applyISAInterest(userDetails);
                    System.out.println("ISAAccount: " + userDetails.getBalance());
                }
            } else if (accessChoice == 3 && userDetails.getAccountType().equalsIgnoreCase("Business")) {
                // Check if it's April
                LocalDate customDate = LocalDate.of(2023, Month.APRIL, 1);
                if (customDate.getMonth() == Month.APRIL) {
                    // Apply business account charges if it's April
                    Update.chargeBusinessAccount(userDetails);
                    System.out.println("businessAccount: " + userDetails.getBalance());
                }
            } else {
                System.out.println("Invalid account type choice for the specified username.");
            }
        } else {
            System.out.println("User not found.");
        }
    }

    public static Map<String, AccessAccount.UserDetails> loadUserDetailsFromFile(String filePath) {
        Map<String, AccessAccount.UserDetails> userDatabase = new HashMap<>();
        try {
            File file = new File(filePath);
            Scanner fileScanner = new Scanner(file);
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 10) {
                    String username = parts[0].trim();
                    String accountNumber = parts[6].trim();
                    String sortCode = parts[7].trim();
                    String balance = parts[9].trim();
                    String accountType = parts[5].trim();
                    AccessAccount.UserDetails userDetails = new AccessAccount.UserDetails(username, accountType, balance, accountNumber, sortCode);
                    userDatabase.put(username, userDetails);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        }
        return userDatabase;
    }
}

import java.io.*;
import java.util.Scanner;

public class Menu {

    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);

        boolean tellerLoggedIn = TellerLogin.login();

        if (tellerLoggedIn) {

            boolean existingUser = UserAuthentication.isExistingUser(scanner);
            if (!existingUser) {
                System.out.println("You are a new user. Please create an account.");
                AccountCreation.createAccount(scanner);



            }

            while (true) {




                    System.out.println("Main Menu\n1. Create Account\n2. Access Account\n3. Help\n4. Customer Exit\n5. Sign out");
                    System.out.println("Enter your choice (1-5):");

                    int choice = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    switch (choice) {
                        case 1:
                            // createAccount(scanner);
                            AccountCreation.createAccount(scanner);
                            break;
                        case 2:
                            // Implement account access logic here
                            AccessAccount.access(scanner);

                            break;
                        case 3:
                            // Display the help file here
                            // You can implement the logic to read and display the file as you did before
                            displayHelpFile();
                            break;
                        case 4:
                            System.out.println("Exiting...");
                            existingUser = false; // Log out by setting existingUser to false
                            existingUser = UserAuthentication.isExistingUser(scanner);
                            break;
                        case 5:
                            System.out.println("Goodbye");
                            System.exit(0);
                            break;
                        default:
                            System.out.println("Invalid choice. Please try again.");

                    }

            }
        }

        scanner.close();
    }

    // Method to display help file
    private static void displayHelpFile() {
        try (InputStream inputStream = Menu.class.getResourceAsStream("/FAQ.txt")) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String contentLine = reader.readLine();
            while (contentLine != null) {
                System.out.println(contentLine);
                contentLine = reader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
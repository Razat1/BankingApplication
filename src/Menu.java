import java.util.Scanner;
public class Menu {
    private  static TellerLogin tellerLogin = new TellerLogin();
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("Main Menu");
            System.out.println("1. Create Account");
            System.out.println("2. Access Account");
            System.out.println("3. Help");
            System.out.println("4. Exit");
            System.out.print("Enter your choice (1-4): ");

            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Creating a new account...");
                    // Add your code for creating an account here
                    break;
                case 2:
                    System.out.println("Accessing your account...");
                    tellerLogin.login(); // Call the login method from TellerLogin
                    break;
                case 3:
                    System.out.println("Help: Here is some help information.");
                    // Add your code for displaying help information here
                    break;
                case 4:
                    System.out.println("Exiting the program. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option (1-4).");
            }
        } while (choice != 4);

        scanner.close();
    }
}
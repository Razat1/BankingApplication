import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class PersonalAccountAccess {
    public static void AccessAccount() {
        Scanner userAccess = new Scanner(System.in);
        boolean exit = false;
        while (!exit) {
            System.out.println("Please select whether you would like to \n1.View \n2.Withdraw \n3.Deposit \n4.Transfer \n5.Exit");

            int userChoice = userAccess.nextInt();
            userAccess.nextLine();
            switch (userChoice) {
                case 1:
                    System.out.println("Please provide customer username: ");
                    String username = userAccess.nextLine();
                    System.out.println("Please provide unique pin: ");
                    String uniquePin = userAccess.nextLine();
                    boolean verified = verifyPIN(username, uniquePin);

                    if (verified) {
                        System.out.println("PIN is verified. You can view account details here.");
                        displayAccessFile(username);
                        System.out.println("Do you want to perform more actions (1 for yes, 2 to return to the menu):");
                        int actionChoice = userAccess.nextInt();
                        userAccess.nextLine();
                        if (actionChoice == 2) {
                            exit = true; // Set the exit flag to true to return to the menu
                        }
                    } else {
                        System.out.println("Invalid PIN or username. Access denied.");
                    }
                    break;

                case 2:
                    //
                case 3:
                    //
                case 4:
                    //
                case 5:
                exit = true;
                default:
                    System.out.println("Invalid choice");

            }
        }
        }
        private static boolean verifyPIN (String username, String enteredPIN){
            // Define the path where customer files are stored
            String path = "C:\\Users\\user\\Desktop\\NovusGroupProject\\BankingApplication\\CreateAccountFiles\\";

            // Construct the filename based on the username
            String fileName = path + username + ".txt";

            try {
                BufferedReader reader = new BufferedReader(new FileReader(fileName));
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.startsWith("PIN: ")) {
                        String storedPIN = line.substring(5); // Extract the stored PIN
                        return storedPIN.equals(enteredPIN);
                    }
                }
                reader.close();
            } catch (IOException e) {
                // Handle file reading errors
            }
            return false; // PIN verification failed
        }

        private static void displayAccessFile (String username){
            // Define the path where access files are stored
            String path = "C:\\Users\\user\\Desktop\\NovusGroupProject\\BankingApplication\\AccessAccountFiles\\";

            // Construct the filename based on the username
            String fileName = path + username + ".txt";

            try {
                BufferedReader reader = new BufferedReader(new FileReader(fileName));
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
                reader.close();
            } catch (IOException e) {
                System.out.println("Error occurred while reading the access file.");
            }
        }


    }



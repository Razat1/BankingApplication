import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class TellerLogin {
    public static boolean login() {
        Scanner teller = new Scanner(System.in);
        System.out.println("Welcome, please enter your username: ");
        int maxAttempts = 3;
        int attempts = 0;

        try (BufferedReader br = new BufferedReader(new FileReader("src/teller_credentials.txt"))) {
            String line;
            String[] arrName = new String[5];
            String[] arrPasswords = new String[5];

            int index = 0;
            while ((line = br.readLine()) != null && index < 5) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    arrName[index] = parts[0].trim();
                    arrPasswords[index] = parts[1].trim();
                    index++;
                }
            }

            //do-while loop started
            do {
                //prompt user to enter username
                String username = teller.nextLine();

                //checks if username is empty and prompts user to try again
                if (username.isEmpty()) {
                    System.out.println("Username cannot be empty, try again");
                    attempts++;

                } else {
                    //prompt user to enter password
                    System.out.println("Enter your Password: ");
                    String password = teller.nextLine();
                    //loop initialised to go through arrays to check if user input matches
                    boolean loginSuccessful = false;
                    for (int i = 0; i < arrName.length; i++) {
                        if (username.equalsIgnoreCase(arrName[i]) && password.equals(arrPasswords[i])) {
                            loginSuccessful = true;
                            break;
                        }
                    }
                    //messages printed depending on whether login is successful or not
                    if (loginSuccessful) {
                        System.out.println("Access granted! Welcome " + username);
                        return true;
                    } else {
                        System.out.println("Access denied!");
                        attempts++;
                        //break;

                    }
                }
                //shows how many attempts left
                System.out.println("You have " + (maxAttempts - attempts) + " attempt(s) left.");
                if (attempts < 3) {
                    System.out.println("Please enter your username:");
                }
                //loop closed after number of attempts exceed max
            } while (attempts < maxAttempts);
        }catch (IOException e) {
            e.printStackTrace();
        }
        teller.close();
        return false;
    }
}
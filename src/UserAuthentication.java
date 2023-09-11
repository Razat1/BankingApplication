import java.io.*;
import java.util.Scanner;
import java.util.Random;
class UserAuthentication {
    private static String enteredUsername;
    //make username
    public static String getEnteredUsername() {
        return enteredUsername;
    }
    public static boolean isExistingUser(Scanner scanner) {
        System.out.print("Is the customer an existing user? (yes/no): ");
        String answer = scanner.nextLine().toLowerCase();


        if (answer.equals("yes")) {
            // Check if the user exists in the user data file
            System.out.println("Enter your username: ");
            enteredUsername = scanner.nextLine();

            // You can implement user existence check here
            boolean userExists = checkUserExists(enteredUsername);

            if (userExists) {
                // User exists, perform additional authentication
                String password = getPasswordForUser(enteredUsername);
                if (authenticateUser(scanner, password)) {
                    System.out.println("Authentication success");
                    return true;
                } else {
                    System.out.println("Authentication failed.");
                }
            } else {
                System.out.println("User does not exist.");
            }
        }  do {
            System.out.print("Enter a username: ");
            enteredUsername = scanner.nextLine();
            boolean userExists = checkUserExists(enteredUsername);
            if (userExists) {
                System.out.println("User already exists. Try again.");
            } else {
                String generatedPassword = generateRandomPassword();
                storeUserDetails(enteredUsername, generatedPassword);
                System.out.println("User registered successfully.");
                System.out.println("Generated Password: " + generatedPassword);
                return false; // New user, no need for further checks
            }
        } while (true);


    }

    // Check if the user exists based on the username
    private static boolean checkUserExists(String enteredUsername) {

        try (InputStream inputStream = Menu.class.getResourceAsStream("/user_details.txt")) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String contentLine;
            while ((contentLine = reader.readLine()) != null) {
                String[] parts = contentLine.split(",");
                if (parts.length >= 2) { // Ensure at least username and password exist
                    String usernameFromFile = parts[0];
                    if (usernameFromFile.equals(enteredUsername)) {
                        return true; // User exists
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Get the user's password based on the username
    private static String getPasswordForUser(String enteredUsername) {
        try (InputStream inputStream = Menu.class.getResourceAsStream("/user_details.txt")) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String contentLine;
            while ((contentLine = reader.readLine()) != null) {
                String[] parts = contentLine.split(",");
                if (parts.length >= 2) { // Ensure at least username and password exist
                    String usernameFromFile = parts[0];
                    String passwordFromFile = parts[1];
                    if (usernameFromFile.equals(enteredUsername)) {
                        return passwordFromFile;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null; // User not found or password not available
    }

    // Perform additional authentication by asking the user to enter characters one by one from the password
    private static boolean authenticateUser(Scanner scanner, String password) {
        if (password == null) {
            return false; // User not found or password not available
        }

        int passwordLength = password.length();
        int maxTries = 4; // Initial try + 3 retries

        for (int tryCount = 1; tryCount <= maxTries; tryCount++) {
            // Generate random positions for characters from the password
            Random random = new Random();
            int[] randomPositions = new int[3]; // Assuming you want 3 random positions

            for (int i = 0; i < 3; i++) {
                randomPositions[i] = random.nextInt(passwordLength) + 1; // Adjust positions to start from 1
            }

            System.out.println("Attempt " + tryCount + ":");
            System.out.println("Enter the character at position " + randomPositions[0] + " from your password:");
            char enteredChar1 = scanner.nextLine().charAt(0);

            System.out.println("Enter the character at position " + randomPositions[1] + " from your password:");
            char enteredChar2 = scanner.nextLine().charAt(0);

            System.out.println("Enter the character at position " + randomPositions[2] + " from your password:");
            char enteredChar3 = scanner.nextLine().charAt(0);

            // Verify if entered characters match the characters at random positions in the password
            boolean authenticationSuccessful = true;
            for (int position : randomPositions) {
                if (position >= 1 && position <= passwordLength) {
                    char expectedChar = password.charAt(position - 1); // Adjust positions to be zero-based
                    if (position == randomPositions[0] && enteredChar1 != expectedChar ||
                            position == randomPositions[1] && enteredChar2 != expectedChar ||
                            position == randomPositions[2] && enteredChar3 != expectedChar) {
                        authenticationSuccessful = false;
                        break;
                    }
                } else {
                    authenticationSuccessful = false;
                    break;
                }
            }

            if (authenticationSuccessful) {
                return true; // Authentication successful
            } else {
                if (tryCount < maxTries) {
                    System.out.println("Incorrect characters entered. Please try again.");
                } else {
                    System.out.println("Authentication failed. You've used all your attempts.");
                }
            }
        }

        return false; // Authentication failed after all attempts
    }
    private static String generateRandomPassword() {
        // Generate a random 8-character password using uppercase letters, lowercase letters, and digits
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder password = new StringBuilder(8);
        for (int i = 0; i < 8; i++) {
            int randomIndex = random.nextInt(characters.length());
            password.append(characters.charAt(randomIndex));
        }
        return password.toString();
    }
    private static void storeUserDetails(String username, String password) {
       // Store the username and password in the user_details.txt file
      try (FileWriter fileWriter = new FileWriter("src/user_details.txt", true);
           BufferedWriter writer = new BufferedWriter(fileWriter)) {
           writer.write(username + "," + password);
           writer.newLine();
      } catch (IOException e) {
           e.printStackTrace();
      }
    }
}
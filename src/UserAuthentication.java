import java.io.*;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Random;
import java.util.Set;

class UserAuthentication {
    private static String enteredUsername;

    public static String getEnteredUsername() {
        return enteredUsername;
    }

    public static boolean isExistingUser(Scanner scanner) {
        System.out.print("Is the customer an existing user? (yes/no): ");
        String answer = scanner.nextLine().toLowerCase();


        String generatedPassword = null;
        if (answer.equals("yes") || enteredUsername != null) {
            System.out.println("Enter your username: ");
            enteredUsername = scanner.nextLine();
            boolean userExists = checkUserExists(enteredUsername);

            if (userExists) {
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
        }

        do {
            System.out.print("Enter a username: ");
            enteredUsername = scanner.nextLine();
            boolean userExists = checkUserExists(enteredUsername);
            if (userExists) {
                System.out.println("User already exists. Try again.");
            } else {
                generatedPassword = generateRandomPassword();
                storeUserDetails(enteredUsername, generatedPassword);
                System.out.println("User registered successfully.");
                System.out.println("Generated Password: " + generatedPassword);
                updateUserDetails(enteredUsername, generatedPassword);

                // Call the readFile method to display the updated file
                try {
                    File file = new File("src/user_details.txt");
                    long fileLength = file.length();
                    readFile(file, fileLength);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return false; // New user, no need for further checks
            }
        } while (true);
    }


    // Check if the user exists based on the username
    private static boolean checkUserExists(String enteredUsername) {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/user_details.txt"))) {
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
        String filePath = "src/user_details.txt"; // Replace with the correct path to your user data file

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String contentLine;
            while ((contentLine = reader.readLine()) != null) {
                String[] parts = contentLine.split(",");
                if (parts.length >= 2) {
                    String usernameFromFile = parts[0];
                    String passwordFromFile = parts[1];
                    if (usernameFromFile.equals(enteredUsername)) {
                        // Call the readFile method to display the file contents
                        try {
                            File file = new File(filePath);
                            long fileLength = file.length();
                            readFile(file, fileLength);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
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

        Set<Integer> usedPositions = new HashSet<>();

        for (int tryCount = 1; tryCount <= maxTries; tryCount++) {
            // Generate random positions for characters from the password
            Random random = new Random();
            int[] randomPositions = new int[3]; // Assuming you want 3 random positions

            for (int i = 0; i < 3; i++) {
                int position;
                do {
                    position = random.nextInt(passwordLength) + 1; // Adjust positions to start from 1
                } while (usedPositions.contains(position)); // Keep generating until an unused position is found

                usedPositions.add(position); // Mark the position as used

                randomPositions[i] = position;
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
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/user_details.txt", true))) {
            // Append the new user details to the file
            writer.write(username + "," + password);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void updateUserDetails(String username, String newPassword) {
        try {
            File file = new File("src/user_details.txt");
            File tempFile = new File("src/temp.txt");

            BufferedReader reader = new BufferedReader(new FileReader(file));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String line;
            boolean found = false; // To track if the user was found for updating

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2 && parts[0].equals(username)) {
                    // Update the line with the new password
                    line = username + "," + newPassword;
                    found = true;
                }
                writer.write(line + System.lineSeparator());
            }

            reader.close();
            writer.close();

            // Debugging statements
            System.out.println("Found: " + found);

            // Delete the original file and rename the temporary file
            if (found) {
                if (file.delete()) {
                    tempFile.renameTo(file);
                    System.out.println("User details updated successfully.");
                } else {
                    System.out.println("Failed to update user details.");
                }
            } else {
                System.out.println("User not found.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void readFile(File file, long fileLength) throws IOException {
        String line = null;

        try (BufferedReader in = new BufferedReader(new java.io.FileReader(file))) {
            in.skip(fileLength);
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
        }
    }
}
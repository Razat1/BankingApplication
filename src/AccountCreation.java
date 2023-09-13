import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.Scanner;
import java.time.Period;

public class AccountCreation {
    public static void createAccount(Scanner scanner) {
        // Implement account creation logic here
        //enteredUsername
        String username=UserAuthentication.getEnteredUsername();

        System.out.println("Please specify which account you would like to create\n1.Personal Account\n2.ISA Account\n3.Business Account\nEnter your choice (1-3): ");
        Scanner acc_choice = new Scanner(System.in);
        int choice = acc_choice.nextInt();
        Scanner userDetails = new Scanner(System.in);
        StringBuilder report = new StringBuilder();

        //asking user details
        System.out.println("Please provide customer's first name: ");
        String firstname = userDetails.nextLine();
        System.out.println("Please provide customer's last name: ");
        String lastname = userDetails.nextLine();
        //ensuring form of date is valid.
        LocalDate dateOfBirth = null;
        boolean validDateFormat = false;
        //random generated numbers for each.
        int value = 1;

        int years = 0;

//while loop for date format validation
        while (!validDateFormat) {
            System.out.println("Please provide customer's date of birth in format YYYY-MM-DD: ");
            DateTimeFormatter dateformat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String birthInput = userDetails.nextLine();

            try {
                dateOfBirth = LocalDate.parse(birthInput,dateformat);
                System.out.println("Date of Birth: " + dateOfBirth);
                validDateFormat = true;

                LocalDate currentDate = LocalDate.now();
                Period age = Period.between(dateOfBirth, currentDate);
                years = age.getYears();


            } catch (java.time.format.DateTimeParseException e) {
                System.out.println("Invalid date format. Please use yyyy-MM-dd format.");
            }

        }
      //rest of the details asked from user
        System.out.println("Please confirm photo ID proof of customer (Yes/No) : ");

        boolean photoID = userDetails.nextLine().equalsIgnoreCase("Yes");
        System.out.println("Please confirm proof of address of customer. ISA applicants must be UK residents (Yes/No) : ");
        boolean addressID = userDetails.nextLine().equalsIgnoreCase("Yes");
        if (!photoID || !addressID) {
            value = 0;
        }
        int deposit = 0;
        boolean depositConfirm = false;
        boolean businesstype = false;
        boolean businessaddressID = false;
        boolean annualcharge = false;
        boolean chequebook = false;
        boolean overdraft = false;
        String businessname = "N/A";
        String sortCode = null;
        String accountType ="N/A";
        String output = null;
        String accountNumber = null;
        int accChoice = 0;
        //IF THEY CANT PROVIDE ADDRESS AND ID AND BUSINESS IT STOPS

        switch (choice){
            case 1:
                sortCode = "05-04-20";
                System.out.println("Confirm minimum deposit of £1 (Yes/No) : ");
                depositConfirm = userDetails.nextLine().equalsIgnoreCase("Yes");
                if (depositConfirm){
                    deposit ++;
                }
                accountType = "Personal";

                break;
            case 2:

                if (years >= 18) {
                    System.out.println("1. Stocks & Shares");
                    System.out.println("2. Innovative");
                    System.out.println("3. Cash");
                    if (years < 40) {
                        System.out.println("4. Lifetime");
                    }
                }

                if (years < 18) {
                    System.out.println("5. Junior");
                    System.out.print("Which one do you want to create? (1-5): ");
                }
                else {System.out.print("Which one do you want to create? (1-4): ");}


                accChoice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                switch (accChoice) {
                    case 1:
                        accountType = "Stocks & Shares ISA";
                        break;
                    case 2:
                        accountType = "Innovative ISA";
                        break;
                    case 3:
                        accountType = "Cash ISA";
                        break;
                    case 4:
                        if (years < 40) {
                            accountType = "Lifetime ISA";
                            break;
                        }
                    case 5:
                        if (years < 18) {
                            accountType = "Junior ISA";
                            break;
                        }
                    default:
                        accountType = "Invalid choice";
                }
                System.out.println("You have created a " + accountType + " account.");
                sortCode = "05-09-19";

                break;
            case 3:
                //every year 120 taken off
                accountType = "Business";
                sortCode = "05-03-26";
                System.out.println("Is your business a sole traders, limited companies or partnership (yes/no)");
                businesstype = userDetails.nextLine().equalsIgnoreCase("Yes");
                System.out.println("Please provide the name of the business");
                businessname  = userDetails.nextLine();
                System.out.println("Please confirm proof of address of business (Yes/No) : ");
                businessaddressID = userDetails.nextLine().equalsIgnoreCase("Yes");
                System.out.println("Are you aware of the annual charge of £120  (yes/no)");
                annualcharge = userDetails.nextLine().equalsIgnoreCase("Yes");
                System.out.println("Would you like a business chequebook   (yes/no)");
                chequebook = userDetails.nextLine().equalsIgnoreCase("Yes");
                System.out.println("Would you like to have an overdraft of £1000 (yes/no)");
                overdraft = userDetails.nextLine().equalsIgnoreCase("Yes");
                //CHECK IF ITS BEEN A YEAR AND DEDUCT 120
                if (!businesstype || !businessaddressID || !annualcharge) {
                    value = 0;
                }
             break;

        }

        //String filePath = "src/"+username+".txt";
        String accountsfile  ="src/accounts.txt";

        String contentToAppend = "Username,Firstname,Lastname,DateOfBirth,BusinessName,AccountType,AccountNumber,SortCode,Pin,Balance";
        if (value !=0) {

            accountNumber = generateRandomAccountNumber();
            String pin = generateRandomPIN();
            try {
                // Step 1: Check if the file exists
                File file = new File(accountsfile);
                BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
                if (!file.exists() || file.length() == 0) {
                    // Step 2: Create the file if it doesn't exist
                    file.createNewFile();
                    System.out.println("File created: " + accountsfile);
                    writer.write(contentToAppend);
                    writer.write("\n"+username+","+firstname + "," + lastname + "," + dateOfBirth + "," + businessname + "," + accountType + "," + accountNumber + "," + sortCode + "," + pin + "," + deposit + "\n");

                    writer.close();

                } else {

                    writer.write(username+","+firstname + "," + lastname + "," + dateOfBirth + "," + businessname + "," + accountType + "," + accountNumber + "," + sortCode + "," + pin + "," + deposit + "\n");

                    writer.close();
                }

                // Step 3: Append content to the file
                System.out.println("Data appended to the file.");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            System.out.println("The customer cannot create an account due to the answers");
        }

        System.out.println(username + " " + accountNumber + " " + sortCode);
    }
    //code for randomly generated account, sort code and pin numbers
    private static String generateRandomAccountNumber() {
        Random random = new Random();
        StringBuilder accountNumber = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            accountNumber.append(random.nextInt(10));
        }
        return accountNumber.toString();
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


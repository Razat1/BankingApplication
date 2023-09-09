import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Menu {


    public static void main(String[] args) {


        Scanner v = new Scanner(System.in);
        boolean login = TellerLogin.login();
        boolean displayHelpMenu = false;
        //make sure they enter 1-4
        //updated to while loop so the program can run again when exiting access/create account
        while (login) {
            System.out.println("Main Menu\n1.Create Account\n2.Access Account\n3.Help\n4.Exit");
            System.out.println("Enter your choice (1-4):");
            int choice = v.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("1. Personal Account\n2. ISA Account\n3. Business Account");
                    int createchoice = v.nextInt();
                    switch (createchoice) {
                        //STORE USERNAME, ACCOUNT NUMBER, SORTCODE
                        //RAZAT -> PERSONAL
                        //HAMZA -> ISA
                        //SHOPITHA -> BUSINESS
                        case 1:
                            //personal account
                            PersonalAccount.CreateAccount();
                            break;
                        case 2:
                            //isa account
                            break;
                        case 3:
                            //business account
                            break;
                    }

                    break;

                case 2:
                    System.out.println("1. Personal Account\n2. ISA Account\n3. Business Account");
                    int accessChoice = v.nextInt();
                    switch (accessChoice) {
                        case 1:
                            //personal account
                            PersonalAccountAccess.AccessAccount();
                            break;
                        case 2:
                            //isa account
                            break;
                        case 3:
                            //business account
                            break;
                    }


                    //ENTER ACCOUNT, SORT CODE -> SAVED IN FILE -> FILE NAME: USERNAME
                    //SHOW BALANCE ->  TRANSFER, WITHDRAW, DEPOSIT

//put in a file to make code shorter - need to update file so questions are clearly shown
                case 3:
                    displayHelpMenu = true;
                    displayHelpFile();
                    break;

                case 4:
                    System.out.println("Goodbye");
                    break;

            }

        }
    }


    //method to display help file
    private static void displayHelpFile() {
        // Define the path where access files are stored
        String path = "C:\\Users\\user\\Desktop\\NovusGroupProject\\BankingApplication\\Help\\";

        // Construct the filename based on the username
        String fileName = path + "FAQ.txt";

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


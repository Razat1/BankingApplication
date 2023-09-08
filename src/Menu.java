import java.util.Scanner;

public class Menu {


    public static void main(String[] args) {

        Scanner v = new Scanner(System.in);
        boolean login =TellerLogin.login();

        //make sure they enter 1-4
        if (login){
            System.out.println("Main Menu\n1.Create Account\n2.Access Account\n3.Help\n4.Exit") ;
            System.out.println("Enter your choice (1-4):");
            int choice= v.nextInt();
            switch (choice){
                case 1:
                    System.out.println("1.Personal Account\n2.ISA Account\n3.Business Account");
                    int createchoice = v.nextInt();
                    switch (createchoice){
                        //STORE USERNAME, ACCOUNT NUMBER, SORTCODE
                        //RAZAT -> PERSONAL
                        //HAMZA -> ISA
                        //SHOPITHA -> BUSINESS
                        case 1:
                            //personal account
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
                    //ENTER ACCOUNT, SORT CODE -> SAVED IN FILE -> FILE NAME: USERNAME
                    //SHOW BALANCE ->  TRANSFER, WITHDRAW, DEPOSIT


                case 3:
                    System.out.println("1.What documents are accepted as photo ID proof?");
                    System.out.println("Passports and Driver’s Licence");
                    System.out.println("2.What documents are accepted as address-based ID proof?");
                    System.out.println("Utility Bills and Council Letter");
                    System.out.println("3.What is the minimum age requirement for Personal and Business Accounts?");
                    System.out.println("16 Years and older");
                    System.out.println("4.What is the minimum age requirement for ISA Accounts?");
                    System.out.println("16 and over for Cash ISA and 18 for the rest. Under 18s are offered Junior ISA");
                    System.out.println("5.What is the charge of a Business Account?");
                    System.out.println("£120 annually");
                    System.out.println("6.What type of companies can open a Business Account?");
                    System.out.println("Sole Traders, Limited Companies and Partnerships");
                    System.out.println("7.Is a minimum deposit required for a Personal Account?");
                    System.out.println("Yes, a minimum of £1 is required.");
                    System.out.println("8.What is the maximum you can save in a ISA Account?");
                    System.out.println("£20,000");
                    System.out.println("9.What are the loan facilities?");
                    System.out.println("For Business Accounts, you are offered an overdraft and access to loans.");
                    System.out.println("10.What is the APR of the ISA Account?");
                    System.out.println("2.75% on average annual balance");
                    break;

                case 4:
                    System.out.println("Goodbye");
                    break;
            }
        }
        else {
            System.out.println("Access denied");
        }

    }
}

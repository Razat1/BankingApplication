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
                    System.out.println();
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
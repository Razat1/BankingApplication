//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.time.LocalDate;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class AccessAccountTests {
//    private AccessAccount accessAccount;
//
//    @BeforeEach
//    public void setUp() {
//        accessAccount = new AccessAccount();
//        accessAccount.loadUserDetailsFromFile("src/accounts.txt");
//    }
//
//    @Test
//    public void testTransferToAccount_SuccessfulTransfer() {
//        String sourceUsername = "joe";
//        int sourceAccountNumber = 88169372;
//        String sourceSortCode = "05-04-20";
//
//        String destinationUsername = "little";
//        int destinationAccountNumber = 28966085;
//        String destinationSortCode = "05-03-26";
//
//        int transferAmount = 10;
//
//        accessAccount.transferFunds(sourceUsername, sourceAccountNumber, sourceSortCode,
//                destinationUsername, destinationAccountNumber, transferAmount, destinationSortCode);
//
//        int expectedSourceBalance = 60; // Updated based on the transfer logic
//        int expectedDestinationBalance = 280; // Updated based on the transfer logic
//
//        assertEquals(expectedSourceBalance, Integer.parseInt(accessAccount.getBalance(sourceUsername, sourceAccountNumber)));
//        assertEquals(expectedDestinationBalance, Integer.parseInt(accessAccount.getBalance(destinationUsername, destinationAccountNumber)));
//    }
//
//    @Test
//    public void testFindUser_SuccessfulMatch() {
//        String username = "joe";
//        int accountNumber = 88169372;
//        String sortCode = "05-04-20";
//
//        AccessAccount.UserDetails foundUser = accessAccount.findUser(username, accountNumber, sortCode);
//
//        assertNotNull(foundUser);
//        assertEquals(username, foundUser.getUsername());
//        assertEquals(String.valueOf(accountNumber), foundUser.getAccountNumber());
//        assertEquals(sortCode, foundUser.getSortCode());
//    }
//
//    @Test
//    public void testFindUser_UserNotFound() {
//        String username = "nonexistent";
//        int accountNumber = 12345678;
//        String sortCode = "99-99-99";
//
//        AccessAccount.UserDetails foundUser = accessAccount.findUser(username, accountNumber, sortCode);
//
//        assertNull(foundUser);
//    }
//}
//
////
////        private static AccessAccount.UserDetails userAccounts;
////
////        @BeforeAll
////        public static void loadUserAccounts() {
////            // Load user account details from the accounts.txt file
////            AccessAccount.loadUserDetailsFromFile("src/accounts.txt");
////            // Get the list of user accounts from the AccessAccount class
////            userAccounts = AccessAccount.findUser("mary", 52380849, "05-09-19");
////        }
////
////        @Test
////        public void testChargeBusinessAccount() {
////            // Iterate over user accounts and test chargeBusinessAccount method
////            for (AccessAccount.UserDetails userAccount : userAccounts) {
////                if (Update.isBusinessAccount(userAccount)) {
////                    double initialBalance = Double.parseDouble(userAccount.getBalance());
////
////                    // Get the expected annual charge for April
////                    double expectedCharge = (LocalDate.now().getMonthValue() == 4) ? 120.0 : 0.0;
////
////                    // Call the method to be tested
////                    Update.chargeBusinessAccount(userAccount);
////
////                    // Check if the balance has been updated correctly
////                    double updatedBalance = Double.parseDouble(userAccount.getBalance());
////                    assertEquals(initialBalance - expectedCharge, updatedBalance, 0.01);
////                }
////            }
////        }
////
////        @Test
////        public void testApplyISAInterest() {
////            // Iterate over user accounts and test applyISAInterest method
////            for (AccessAccount.UserDetails userAccount : userAccounts) {
////                if (Update.isISAAccount(userAccount)) {
////                    double initialBalance = Double.parseDouble(userAccount.getBalance());
////
////                    // Calculate the expected interest
////                    double interestRate = 2.75 / 100;
////                    double expectedInterest = initialBalance * interestRate;
////
////                    // Call the method to be tested
////                    Update.applyISAInterest(userAccount);
////
////                    // Check if the balance has been updated correctly
////                    double updatedBalance = Double.parseDouble(userAccount.getBalance());
////                    assertEquals(initialBalance + expectedInterest, updatedBalance, 0.01);
////                }
////            }
////        }
////    }

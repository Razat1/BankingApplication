import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AccessAccountTests {
    private AccessAccount accessAccount;

    @BeforeEach
    public void setUp() {
        accessAccount = new AccessAccount();
        accessAccount.loadUserDetailsFromFile("src/accounts.txt");
    }

    @Test
    public void testTransferToAccount_SuccessfulTransfer() {
        String sourceUsername = "joe";
        int sourceAccountNumber = 88169372;
        String sourceSortCode = "05-04-20";

        String destinationUsername = "little";
        int destinationAccountNumber = 28966085;
        String destinationSortCode = "05-03-26";

        int transferAmount = 10;

        accessAccount.transferFunds(sourceUsername, sourceAccountNumber, sourceSortCode,
                destinationUsername, destinationAccountNumber, transferAmount, destinationSortCode);

        int expectedSourceBalance = 90; // Updated based on the transfer logic
        int expectedDestinationBalance = 250; // Updated based on the transfer logic

        assertEquals(expectedSourceBalance, Integer.parseInt(accessAccount.getBalance(sourceUsername, sourceAccountNumber)));
        assertEquals(expectedDestinationBalance, Integer.parseInt(accessAccount.getBalance(destinationUsername, destinationAccountNumber)));
    }

    @Test
    public void testFindUser_SuccessfulMatch() {
        String username = "joe";
        int accountNumber = 88169372;
        String sortCode = "05-04-20";

        AccessAccount.UserDetails foundUser = accessAccount.findUser(username, accountNumber, sortCode);

        assertNotNull(foundUser);
        assertEquals(username, foundUser.getUsername());
        assertEquals(String.valueOf(accountNumber), foundUser.getAccountNumber());
        assertEquals(sortCode, foundUser.getSortCode());
    }

    @Test
    public void testFindUser_UserNotFound() {
        String username = "nonexistent";
        int accountNumber = 12345678;
        String sortCode = "99-99-99";

        AccessAccount.UserDetails foundUser = accessAccount.findUser(username, accountNumber, sortCode);

        assertNull(foundUser);
    }
}


import java.time.LocalDate;

public class AnnualChargeTest {
    public static void main(String[] args) {
        // Convert the balance from double to String
        String initialBalance = String.valueOf(1000.00);

        // Create a business account with the balance as a String
        AccessAccount.UserDetails businessAccount = new AccessAccount.UserDetails("BusinessUser", "123456", "1234", initialBalance, "Business");

        // Define the number of years to simulate
        int numberOfYears = 5; // Change this to the desired number of years

        // Initialize the current date to April 1st of the first year
        LocalDate currentDate = LocalDate.of(LocalDate.now().getYear(), 4, 1);

        // Simulate multiple years
        for (int year = 1; year <= numberOfYears; year++) {
            // Check if the current month is April
            if (currentDate.getMonthValue() == 4) {
                // Apply the annual charge
                Update.chargeBusinessAccount(businessAccount);

                // Verify and print the balance for each year
                System.out.println("Year " + year + ": Business Account Balance after year-end: £" + businessAccount.getBalance());
            }

            // Decrement the balance by 120 after the annual charge is applied
            businessAccount.setBalance(String.valueOf(Double.parseDouble(businessAccount.getBalance()) - 120.0));

            // Increment the current date by one year
            currentDate = currentDate.plusYears(1);
        }
    }
}
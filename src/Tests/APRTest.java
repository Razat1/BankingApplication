import java.time.LocalDate;
import java.text.DecimalFormat;

public class APRTest{
    public static void main(String[] args) {
        // Convert the balance from double to String
        String initialBalance = String.valueOf(1000.00);

        // Create an ISA account with the balance as a String
        AccessAccount.UserDetails isaAccount = new AccessAccount.UserDetails("ISAUser", "123456", "1234", initialBalance, "ISA");

        // Define the number of years to simulate
        int numberOfYears = 5; // Change this to the desired number of years

        // Initialize the current date to April 1st of the first year
        LocalDate currentDate = LocalDate.of(LocalDate.now().getYear(), 4, 1);

        // Define the annual interest rate
        double annualInterestRate = 0.0275; // 2.75%

        // Create a DecimalFormat to format the balance with two decimal places
        DecimalFormat decimalFormat = new DecimalFormat("#0.00");

        // Simulate multiple years
        for (int year = 1; year <= numberOfYears; year++) {
            // Check if the current month is April
            if (currentDate.getMonthValue() == 4) {
                // Apply the annual interest rate
                Update.applyISAInterest(isaAccount);

                // Verify and print the balance for each year
                String formattedBalance = decimalFormat.format(Double.parseDouble(isaAccount.getBalance()));
                System.out.println("Year " + year + ": ISA Account Balance after year-end: £" + formattedBalance);
            }

            // Increment the current date by one year
            currentDate = currentDate.plusYears(1);
        }
    }
}
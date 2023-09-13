import java.time.LocalDate;
import java.text.DecimalFormat;
public class Update {

    private static int lastYearCharged = -1; // Initialize to -1 to ensure it charges in the first year

    public static void chargeBusinessAccount(AccessAccount.UserDetails userAccount) {
        if (isBusinessAccount(userAccount)) {
            // Get the current year to check if the charge has been deducted this year
            LocalDate currentDate = LocalDate.now();
            int currentYear = currentDate.getYear();

            if (currentYear > lastYearCharged) {
                // Deduct the annual charge
                double currentBalance = Double.parseDouble(userAccount.getBalance());
                double annualCharge = 120.0;

                if (currentBalance >= annualCharge) {
                    currentBalance = Double.parseDouble(userAccount.getBalance()) - annualCharge;
                    userAccount.setBalance(String.valueOf(currentBalance));
                    System.out.println("Annual charge of £" + annualCharge + " deducted.");
                    lastYearCharged = currentYear; // Update the last charged year
                } else {
                    System.out.println("Insufficient balance to deduct the annual charge.");
                }
            }
        }
    }
    public static void applyISAInterest(AccessAccount.UserDetails userAccount) {
        // Check if the userAccount represents an ISA account
        if (isISAAccount(userAccount)) {
            double currentBalance = Double.parseDouble(userAccount.getBalance());
            double interestRate = 2.75 / 100; // 2.75% APR

            // Calculate the interest and add it to the balance
            double interest = currentBalance * interestRate;
            currentBalance += interest;

            // Format the interest and balance with two decimal places
            DecimalFormat decimalFormat = new DecimalFormat("#0.00");
            String formattedInterest = decimalFormat.format(interest);
            String formattedBalance = decimalFormat.format(currentBalance);

            // Update the balance in the userAccount
            userAccount.setBalance(formattedBalance);

            System.out.println("ISA interest of £" + formattedInterest + " added.");
        }
    }
    private static boolean isBusinessAccount(AccessAccount.UserDetails userAccount) {
        return "Business" .equalsIgnoreCase(userAccount.getAccountType());
    }

    // Helper method to check if the account is an ISA account
    private static boolean isISAAccount(AccessAccount.UserDetails userAccount) {
        return "ISA" .equalsIgnoreCase(userAccount.getAccountType());
    }
}
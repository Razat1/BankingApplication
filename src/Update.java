import java.time.LocalDate;
public class Update {

    public static void chargeBusinessAccount(AccessAccount.UserDetails userAccount) {

        if (isBusinessAccount(userAccount)) {
            // Apply charges for business accounts
            double currentBalance = Double.parseDouble(userAccount.getBalance());
            double annualCharge = 120.0;

            // Get the current month to check for April
            LocalDate currentDate = LocalDate.now();
            int currentMonth = currentDate.getMonthValue();

            // Check if it's April, and if so, deduct the annual charge
            if (currentMonth == 4) {
                currentBalance -= annualCharge;
                userAccount.setBalance(String.valueOf(currentBalance));
                System.out.println("Annual charge of £" + annualCharge + " deducted.");
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

            // Update the balance in the userAccount
            userAccount.setBalance(String.valueOf(currentBalance));

            System.out.println("ISA interest of £" + interest + " added.");
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
package ExpenseManagementCLI;

class PendingPayment {
    String description;
    double amount;
    String date;

    PendingPayment(String description, double amount, String date) {
        this.description = description;
        this.amount = amount;
        this.date = date;
    }

    @Override
    public String toString() {
        return description + "," + amount + "," + date;
    }

    // Static method to create a PendingPayment from a CSV string
    public static PendingPayment fromString(String str) {
        String[] parts = str.split(",");
        return new PendingPayment(parts[0], Double.parseDouble(parts[1]), parts[2]);
    }
}


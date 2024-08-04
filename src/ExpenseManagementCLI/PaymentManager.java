package ExpenseManagementCLI;

import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;

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

    public class PaymentManager {
    private static final String PENDING_PAYMENTS_FILE = "pendingPayments.txt";
    private static final String PRIORITY_LIST_FILE = "priorityList.txt";

    public static void managepayment() {
        LinkedList<PendingPayment> pendingPayments = loadPendingPayments();
        LinkedList<PendingPayment> priorityList = loadPriorityList();

        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        while (running) {
            System.out.println("Choose an option:");
            System.out.println("1. View Pending Payments List");
            System.out.println("2. Add New Payments to Priority List");
            System.out.println("3. View Priority List");
            System.out.println("4. Edit Priority List");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    displayPendingPayments(pendingPayments);
                    break;
                case 2:
                    getPriorityPaymentsFromUser(priorityList);
                    break;
                case 3:
                    displayPendingPayments(priorityList);
                    break;
                case 4:
                    editPriorityList(priorityList);
                    break;
                case 5:
                    savePendingPayments(pendingPayments);
                    savePriorityList(priorityList);
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.close();
    }

    public static void displayPendingPayments(LinkedList<PendingPayment> payments) {
        System.out.println("Payments List:");
        for (PendingPayment payment : payments) {
            System.out.println(payment);
        }
    }

    public static void getPriorityPaymentsFromUser(LinkedList<PendingPayment> priorityList) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Enter description: ");
            String description = scanner.nextLine();

            System.out.print("Enter amount: ");
            double amount = scanner.nextDouble();
            scanner.nextLine(); // Consume the newline character

            System.out.print("Enter date (yyyy-mm-dd): ");
            String date = scanner.nextLine();

            priorityList.add(new PendingPayment(description, amount, date));

            System.out.print("Do you want to add another priority payment? (yes/no): ");
            String response = scanner.nextLine();
            if (response.equalsIgnoreCase("no")) {
                break;
            }
        }
    }

    public static void editPriorityList(LinkedList<PendingPayment> priorityList) {
        Scanner scanner = new Scanner(System.in);
        displayPendingPayments(priorityList);

        System.out.print("Enter the index of the payment you want to edit (starting from 0): ");
        int index = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        if (index >= 0 && index < priorityList.size()) {
            System.out.print("Enter new description: ");
            String newDescription = scanner.nextLine();

            System.out.print("Enter new amount: ");
            double newAmount = scanner.nextDouble();
            scanner.nextLine(); // Consume the newline character

            System.out.print("Enter new date (yyyy-mm-dd): ");
            String newDate = scanner.nextLine();

            PendingPayment updatedPayment = new PendingPayment(newDescription, newAmount, newDate);
            priorityList.set(index, updatedPayment);

            System.out.println("Payment updated successfully.");
        } else {
            System.out.println("Invalid index. Please try again.");
        }
    }

    public static void savePendingPayments(LinkedList<PendingPayment> payments) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PENDING_PAYMENTS_FILE))) {
            for (PendingPayment payment : payments) {
                writer.write(payment.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving pending payments: " + e.getMessage());
        }
    }

    public static LinkedList<PendingPayment> loadPendingPayments() {
        LinkedList<PendingPayment> payments = new LinkedList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(PENDING_PAYMENTS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                payments.add(PendingPayment.fromString(line));
            }
        } catch (IOException e) {
            System.out.println("Error loading pending payments: " + e.getMessage());
        }
        return payments;
    }

    public static void savePriorityList(LinkedList<PendingPayment> priorityList) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PRIORITY_LIST_FILE))) {
            for (PendingPayment payment : priorityList) {
                writer.write(payment.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving priority list: " + e.getMessage());
        }
    }

    public static LinkedList<PendingPayment> loadPriorityList() {
        LinkedList<PendingPayment> priorityList = new LinkedList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(PRIORITY_LIST_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                priorityList.add(PendingPayment.fromString(line));
            }
        } catch (IOException e) {
            System.out.println("Error loading priority list: " + e.getMessage());
        }
        return priorityList;
    }
}
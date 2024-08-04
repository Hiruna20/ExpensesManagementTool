package ExpenseManagementCLI;

import java.io.*;
import java.util.Scanner;

import static ExpenseManagementCLI.CLI.PaymentManager.managepayment;

public class CLI {

    public static void main(String[] args) {
        LinkedList list = new LinkedList();
        list.loadFromFile();
        mainHome(list);
    }

    public static void mainHome(LinkedList list) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("*******************************************************************WELCOME..!**************************************************************************************\n");
        System.out.print("\tEnter the name to login \n");
        System.out.print("\tType the word 'create' to create user/team\n\t :");
        String namecheck = scanner.nextLine();
        if ("create".equals(namecheck)) {
            createUser(list);
        } else if ("".equals(namecheck)) {
            list.saveToFile();
            System.exit(0);
        } else {
            userHome(namecheck, list);
        }
    }

    private static void createUser(LinkedList list) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the name to add: ");
        String name = scanner.nextLine();
        System.out.print("Enter the income for " + name + ": ");
        double income = scanner.nextDouble();
        System.out.print("Enter the savings for " + name + ": ");
        double savings = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        list.addNode(name, income, savings);
        list.saveToFile();

        mainHome(list);
    }

    private static void userHome(String username, LinkedList list) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("__________________________________________________________________________________________________________________________________________________________________\n\n\tNotice:");
        // Print notice for user
        System.out.print("\n__________________________________________________________________________________________________________________________________________________________________\n");
        System.out.print("\tType the word 'add' to add a comment\n");
        System.out.print("\tType the word 'edit' to edit user\n");
        System.out.print("\tType the word 'view' to view user expences\n");
        System.out.print("\tType the word 'payment' to add/edit/view payments\n");
        System.out.print("\tType the word 'sort' to sort user data\n");
        System.out.print("\tType the word 'search' to search expenses\n");
        System.out.print("\tType the word 'back' to go back\n:-");
        String keyword = scanner.nextLine();
        if ("add".equals(keyword)) {
            takeComment(username, list);
        } else if ("edit".equals(keyword)) {
            userEdit(username, list);
        } else if ("payment".equals(keyword)) {
            managepayment();
        } else if ("sort".equals(keyword)) {
            list.printList();
        } else if ("search".equals(keyword)) {
            searchExpenses(username,list);
        } else if ("back".equals(keyword)) {
            mainHome(list);
        }
    }

    private static void takeComment(String username, LinkedList list) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your comment: ");
        String comment = scanner.nextLine();
        System.out.print("Enter the expense: ");
        double expense = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        list.addCommentToNode(username, comment, expense);
        list.saveToFile();
    }

    private static void userEdit(String username, LinkedList list) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the new name: ");
        String newName = scanner.nextLine();
        System.out.print("Enter the new income: ");
        double newIncome = scanner.nextDouble();
        System.out.print("Enter the new savings: ");
        double newSavings = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        list.editUserData(username, newName, newIncome, newSavings);
        list.saveToFile();
    }

    private static void sortExpenses(String username, LinkedList list) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Sorting options: sort by\n\t1. date\n\t2. value\n(Type the word 'back' to go back)\n:-");
        String sortType = scanner.nextLine();
        switch (sortType) {
            case "date":
                //list.sortExpensesByDate();
                break;
            case "value":
                //list.sortExpensesByValue();
                break;
            case "back":
                userHome(username, list);
                return;
            default:
                System.out.println("Invalid option. Please enter 'date' or 'value'.");
                break;
        }
        userHome(username, list);
    }

    private static void searchExpenses(String username,LinkedList list) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Search options: search by(Type a above search type)\n\t1.name\n\t2.date\n\t3.value\n(Type the word 'back' to go back)\n:-");
        String searchType = scanner.nextLine();
        if(searchType!=""){
            switch (searchType) {
                case "name":
                    System.out.print("Enter the name to search: ");
                    String name = scanner.nextLine();
                    list.searchExpensesByName(name);
                    break;
                case "date":
                    System.out.print("Enter the date to search for expenses (yyyy-MM-dd): ");
                    String date = scanner.nextLine();
                    list.searchExpensesByDate(date);
                    break;
                case "value":
                    System.out.print("Enter the expense value to search for: ");
                    double value = scanner.nextDouble();
                    scanner.nextLine();
                    list.searchExpensesByValue(value);
                    break;
                case "back":
                    userHome(scanner.nextLine(), list);
                    break;
            }
        }
        else{
            System.out.print("****Please enter correct key word!****");
        }
        userHome(username, list);
    }



   /* public static void PaymentManager() {



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
    }

*/

    public static class PaymentManager {
    private static final String PENDING_PAYMENTS_FILE = "pendingPayments.txt";
    private static final String PRIORITY_LIST_FILE = "priorityList.txt";

    public static void managepayment() {
        java.util.LinkedList<PendingPayment> pendingPayments = loadPendingPayments();
        java.util.LinkedList<PendingPayment> priorityList = loadPriorityList();

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

    public static void displayPendingPayments(java.util.LinkedList<PendingPayment> payments) {
        System.out.println("Payments List:");
        for (PendingPayment payment : payments) {
            System.out.println(payment);
        }
    }

    public static void getPriorityPaymentsFromUser(java.util.LinkedList<PendingPayment> priorityList) {
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

    public static void editPriorityList(java.util.LinkedList<PendingPayment> priorityList) {
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

    public static void savePendingPayments(java.util.LinkedList<PendingPayment> payments) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PENDING_PAYMENTS_FILE))) {
            for (PendingPayment payment : payments) {
                writer.write(payment.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving pending payments: " + e.getMessage());
        }
    }

    public static java.util.LinkedList<PendingPayment> loadPendingPayments() {
        java.util.LinkedList<PendingPayment> payments = new java.util.LinkedList<>();
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

    public static void savePriorityList(java.util.LinkedList<PendingPayment> priorityList) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PRIORITY_LIST_FILE))) {
            for (PendingPayment payment : priorityList) {
                writer.write(payment.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving priority list: " + e.getMessage());
        }
    }

    public static java.util.LinkedList<PendingPayment> loadPriorityList() {
        java.util.LinkedList<PendingPayment> priorityList = new java.util.LinkedList<>();
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
}

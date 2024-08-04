/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ExpenseManagementCLI;

import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.LinkedList;


public class UserInputLinkedList {
    private static final String FILE_NAME = "user_inputs.txt";
    private String userName;
    
    public void manageEntries(String userName) {
        this.userName = userName;
		
        LinkedList<IncomeEntry> incomeEntries = new LinkedList<>();
        LinkedList<ExpenseEntry> expenseEntries = new LinkedList<>();

        Scanner scanner = new Scanner(System.in);
        
        // Load existing data from the file
        loadFromFile(incomeEntries, expenseEntries, userName);

        boolean restart = false;
        while (true) {
            System.out.print("___________________________________________________________________________________________________________________________________________________________________________________________________________________________\n\t");
            System.out.print("\n\t\t\t\t***MANAGE EXPECES & INCOME***\n\n");
            System.out.println("\nHello, " + userName + "!");
            System.out.println("Choose an option:");
            System.out.println("1. Add new entries");
            System.out.println("2. View existing entries");
            System.out.println("3. Calculate total incomes and expenses");
            System.out.println("4. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the leftover newline character

            switch (choice) {
                case 1:
                    addNewEntries(scanner, incomeEntries, expenseEntries);
                    saveToFile(incomeEntries, expenseEntries);
                    break;

                case 2:
                    viewExistingEntries(scanner, incomeEntries, expenseEntries);
                    break;

                case 3:
                    calculateTotals(incomeEntries, expenseEntries);
                    break;

                case 4:
                    restart = true;
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            //hope this work
            while (restart){
                CLI.gohome();
                restart = false;
            }

        }
    }

    private void addNewEntries(Scanner scanner, LinkedList<IncomeEntry> incomeEntries, LinkedList<ExpenseEntry> expenseEntries) {
        while (true) {
            System.out.println("Choose entry type:");
            System.out.println("1. Add new incomes");
            System.out.println("2. Add new expenses");
            System.out.println("3. Back to main menu");

            int entryType = scanner.nextInt();
            scanner.nextLine(); // Consume the leftover newline character

            switch (entryType) {
                case 1:
                    addIncomes(scanner, incomeEntries);
                    return; // Return to main menu after adding incomes
                case 2:
                    addExpenses(scanner, expenseEntries);
                    return; // Return to main menu after adding expenses
                case 3:
                    return; // Return to main menu
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void addIncomes(Scanner scanner, LinkedList<IncomeEntry> incomeEntries) {
        System.out.print("Enter the number of new incomes you want to provide: ");
        int numberOfIncomes = scanner.nextInt();
        scanner.nextLine(); // Consume the leftover newline character

        for (int i = 0; i < numberOfIncomes; i++) {
            System.out.print("Enter income " + (i + 1) + ": ");
            String income = scanner.nextLine();

            System.out.print("Enter description for income " + (i + 1) + ": ");
            String description = scanner.nextLine();

            LocalDate date = LocalDate.now(); // Get the current date
            incomeEntries.add(new IncomeEntry(userName, income, description, date)); // Add the income entry to the linked list
        }
    }

    private void addExpenses(Scanner scanner, LinkedList<ExpenseEntry> expenseEntries) {
        System.out.print("Enter the number of new expenses you want to provide: ");
        int numberOfExpenses = scanner.nextInt();
        scanner.nextLine(); // Consume the leftover newline character

        for (int i = 0; i < numberOfExpenses; i++) {
            System.out.print("Enter expense " + (i + 1) + ": ");
            String expense = scanner.nextLine();

            System.out.print("Enter description for expense " + (i + 1) + ": ");
            String description = scanner.nextLine();

            LocalDate date = LocalDate.now(); // Get the current date
            expenseEntries.add(new ExpenseEntry(userName, expense, description, date)); // Add the expense entry to the linked list
        }
    }

    private static void viewExistingEntries(Scanner scanner, LinkedList<IncomeEntry> incomeEntries, LinkedList<ExpenseEntry> expenseEntries) {
        while (true) {
            System.out.println("Choose entry type to view:");
            System.out.println("1. View incomes");
            System.out.println("2. View expenses");
            System.out.println("3. Back to main menu");

            int viewType = scanner.nextInt();
            scanner.nextLine(); // Consume the leftover newline character

            switch (viewType) {
                case 1:
                    viewEntries(scanner, new ArrayList<>(incomeEntries), "Income");
                    return; // Return to main menu after viewing incomes
                case 2:
                    viewEntries(scanner, new ArrayList<>(expenseEntries), "Expense");
                    return; // Return to main menu after viewing expenses
                case 3:
                    return; // Return to main menu
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void viewEntries(Scanner scanner, List<?> entries, String type) {
        while (true) {
            System.out.println("Choose a sorting option:");
            System.out.println("1. View by description (alphabetical order)");
            System.out.println("2. View by description (descending alphabetical order)");
            System.out.println("3. View by date (chronological order)");
            System.out.println("4. View by date (descending order)");
            System.out.println("5. View by value (ascending order)");
            System.out.println("6. View by value (descending order)");
            System.out.println("7. Back to previous menu");

            int sortOption = scanner.nextInt();
            scanner.nextLine(); // Consume the leftover newline character

            List<?> sortedEntries;
            switch (sortOption) {
                case 1:
                    sortedEntries = sortByDescription(entries, true);
                    break;
                case 2:
                    sortedEntries = sortByDescription(entries, false);
                    break;
                case 3:
                    sortedEntries = sortByDate(entries, true);
                    break;
                case 4:
                    sortedEntries = sortByDate(entries, false);
                    break;
                case 5:
                    sortedEntries = sortByValue(entries, true);
                    break;
                case 6:
                    sortedEntries = sortByValue(entries, false);
                    break;
                case 7:
                    return; // Return to the previous menu
                default:
                    System.out.println("Invalid choice. Please try again.");
                    continue;
            }

            viewList(sortedEntries, type);
        }
    }

    private static List<?> sortByDescription(List<?> entries, boolean ascending) {
        List<Object> sortedList = new ArrayList<>(entries);
        if (ascending) {
            sortedList.sort(Comparator.comparing(entry -> {
                if (entry instanceof IncomeEntry) {
                    return ((IncomeEntry) entry).getDescription();
                } else if (entry instanceof ExpenseEntry) {
                    return ((ExpenseEntry) entry).getDescription();
                }
                return "";
            }));
        } else {
            sortedList.sort(Comparator.comparing(entry -> {
                if (entry instanceof IncomeEntry) {
                    return ((IncomeEntry) entry).getDescription();
                } else if (entry instanceof ExpenseEntry) {
                    return ((ExpenseEntry) entry).getDescription();
                }
                return "";
            }).reversed());
        }
        return sortedList;
    }

    private static List<?> sortByDate(List<?> entries, boolean ascending) {
        List<Object> sortedList = new ArrayList<>(entries);
        if (ascending) {
            sortedList.sort(Comparator.comparing(entry -> {
                if (entry instanceof IncomeEntry) {
                    return ((IncomeEntry) entry).getDate();
                } else if (entry instanceof ExpenseEntry) {
                    return ((ExpenseEntry) entry).getDate();
                }
                return LocalDate.MIN;
            }));
        } else {
            sortedList.sort(Comparator.comparing(entry -> {
                if (entry instanceof IncomeEntry) {
                    return ((IncomeEntry) entry).getDate();
                } else if (entry instanceof ExpenseEntry) {
                    return ((ExpenseEntry) entry).getDate();
                }
                return LocalDate.MIN;
            }).reversed());
        }
        return sortedList;
    }

    private static List<?> sortByValue(List<?> entries, boolean ascending) {
        List<Object> sortedList = new ArrayList<>(entries);
        if (ascending) {
            sortedList.sort(Comparator.comparingDouble(entry -> {
                if (entry instanceof IncomeEntry) {
                    return Double.parseDouble(((IncomeEntry) entry).getIncome());
                } else if (entry instanceof ExpenseEntry) {
                    return Double.parseDouble(((ExpenseEntry) entry).getExpense());
                }
                return 0.0;
            }));
        } else {
            sortedList.sort(Comparator.comparingDouble(entry -> {
                if (entry instanceof IncomeEntry) {
                    return Double.parseDouble(((IncomeEntry) entry).getIncome());
                } else if (entry instanceof ExpenseEntry) {
                    return Double.parseDouble(((ExpenseEntry) entry).getExpense());
                }
                return 0.0;
            }).reversed());
        }
        return sortedList;
    }

    private static void viewList(List<?> entries, String type) {
        System.out.println("The " + type.toLowerCase() + "s are:");
        for (Object entry : entries) {
            if (entry instanceof IncomeEntry) {
                IncomeEntry income = (IncomeEntry) entry;
                System.out.println(income.getIncome() + " - " + income.getDescription() + " - " + income.getDate());
            } else if (entry instanceof ExpenseEntry) {
                ExpenseEntry expense = (ExpenseEntry) entry;
                System.out.println(expense.getExpense() + " - " + expense.getDescription() + " - " + expense.getDate());
            }
        }
    }

    private static void calculateTotals(LinkedList<IncomeEntry> incomeEntries, LinkedList<ExpenseEntry> expenseEntries) {
        double totalIncome = 0.0;
        double totalExpense = 0.0;

        for (IncomeEntry entry : incomeEntries) {
            totalIncome += Double.parseDouble(entry.getIncome());
        }

        for (ExpenseEntry entry : expenseEntries) {
            totalExpense += Double.parseDouble(entry.getExpense());
        }

        System.out.println("Total incomes: " + totalIncome);
        System.out.println("Total expenses: " + totalExpense);
        System.out.println("Net balance: " + (totalIncome - totalExpense));
    }

    private static void saveToFile(LinkedList<IncomeEntry> incomeEntries, LinkedList<ExpenseEntry> expenseEntries) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (IncomeEntry entry : incomeEntries) {
                writer.write("Income|" + entry.getUserName() + "|" + entry.getIncome() + "|" + entry.getDescription() + "|" + entry.getDate());
                writer.newLine();
            }
            for (ExpenseEntry entry : expenseEntries) {
                writer.write("Expense|" + entry.getUserName() + "|" + entry.getExpense() + "|" + entry.getDescription() + "|" + entry.getDate());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    private static void loadFromFile(LinkedList<IncomeEntry> incomeEntries, LinkedList<ExpenseEntry> expenseEntries, String userName) {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 5) {
                    String type = parts[0];
                    String user = parts[1];
                    String amount = parts[2];
                    String description = parts[3];
                    LocalDate date = LocalDate.parse(parts[4]);

                    if (user.equals(userName)) {
                        if (type.equals("Income")) {
                            incomeEntries.add(new IncomeEntry(user, amount, description, date));
                        } else if (type.equals("Expense")) {
                            expenseEntries.add(new ExpenseEntry(user, amount, description, date));
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading from file: " + e.getMessage());
        }
    }
}

// Helper class to represent an income entry with a description and date
class IncomeEntry {
    private final String userName;
    private final String income;
    private final String description;
    private final LocalDate date;

    public IncomeEntry(String userName, String income, String description, LocalDate date) {
        this.userName = userName;
        this.income = income;
        this.description = description;
        this.date = date;
    }

    public String getUserName() {
        return userName;
    }

    public String getIncome() {
        return income;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDate() {
        return date;
    }
}

// Helper class to represent an expense entry with a description and date
class ExpenseEntry {
    private final String userName;
    private final String expense;
    private final String description;
    private final LocalDate date;

    public ExpenseEntry(String userName, String expense, String description, LocalDate date) {
        this.userName = userName;
        this.expense = expense;
        this.description = description;
        this.date = date;
    }

    public String getUserName() {
        return userName;
    }

    public String getExpense() {
        return expense;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDate() {
        return date;
    }
}

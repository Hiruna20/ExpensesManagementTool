package ExpenseManagement;

import java.util.Scanner;

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
        System.out.print("\tType the word 'sort' to sort user data\n");
        System.out.print("\tType the word 'search' to search expenses\n");
        System.out.print("\tType the word 'back' to go back\n:-");
        String keyword = scanner.nextLine();
        if ("add".equals(keyword)) {
            takeComment(username, list);
        } else if ("edit".equals(keyword)) {
            userEdit(username, list);
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
}

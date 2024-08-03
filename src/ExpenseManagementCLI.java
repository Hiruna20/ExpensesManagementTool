import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

class Comment {
    String text;
    String date;
    double expense;

    Comment(String text, String date, double expense) {
        this.text = text;
        this.date = date;
        this.expense = expense;
    }
}

class Node {
    String name;
    double income;
    double savings;
    List<Comment> comments;
    Node next;

    Node(String name, double income, double savings) {
        this.name = name;
        this.income = income;
        this.savings = savings;
        this.comments = new ArrayList<>();
        this.next = null;
    }
}

class LinkedList {
    Node head;
    private static final String FILE_NAME = "user_data.txt";

    public void addNode(String name, double income, double savings) {
        Node newNode = new Node(name, income, savings);

        if (head == null) {
            head = newNode;
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
    }

    public Node findNodeByName(String name) {
        Node current = head;
        while (current != null) {
            if (current.name.equals(name)) {
                return current;
            }
            current = current.next;
        }
        return null; // User not found
    }

    public void addCommentToNode(String name, String commentText, double expense) {
        Node node = findNodeByName(name);
        if (node != null) {
            String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            node.comments.add(new Comment(commentText, date, expense));
        } else {
            System.out.println("User not found.");
        }
    }

    public void editUserData(String currentName, String newName, double newIncome, double newSavings) {
        Node node = findNodeByName(currentName);
        if (node != null) {
            node.name = newName;
            node.income = newIncome;
            node.savings = newSavings;
            System.out.println("User data updated successfully.");
        } else {
            System.out.println("User not found.");
        }
    }

    public void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            Node temp = head;
            if (temp == null) {
                System.out.println("The linked list is empty. Nothing to save.");
            }
            while (temp != null) {
                writer.write(temp.name + "," + temp.income + "," + temp.savings);
                for (Comment comment : temp.comments) {
                    writer.write("," + comment.text + "," + comment.date + "," + comment.expense);
                }
                writer.newLine();
                temp = temp.next;
            }
            System.out.println("Data saved successfully.");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    public void loadFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    String name = parts[0];
                    double income = Double.parseDouble(parts[1]);
                    double savings = Double.parseDouble(parts[2]);
                    Node newNode = new Node(name, income, savings);

                    for (int i = 3; i < parts.length; i += 3) {
                        if (i + 2 < parts.length) {
                            String commentText = parts[i];
                            String commentDate = parts[i + 1];
                            double expense = Double.parseDouble(parts[i + 2]);
                            newNode.comments.add(new Comment(commentText, commentDate, expense));
                        } else {
                            System.out.println("Skipping incomplete comment data for user: " + name);
                        }
                    }

                    if (head == null) {
                        head = newNode;
                    } else {
                        Node current = head;
                        while (current.next != null) {
                            current = current.next;
                        }
                        current.next = newNode;
                    }
                } else {
                    System.out.println("Skipping invalid line: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading from file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error parsing number from file: " + e.getMessage());
        }
    }


    public void printList() {
        Node temp = head;
        if (temp == null) {
            System.out.println("The list is empty.");
        }
        while (temp != null) {
            System.out.println("Name: " + temp.name + ", Income: " + temp.income + ", Savings: " + temp.savings);
            for (Comment comment : temp.comments) {
                System.out.println("  Comment: " + comment.text + " (Date: " + comment.date + ", Expense: $" + comment.expense + ")");
            }
            temp = temp.next;
        }
    }
    
   
    public void searchExpensesByName(String name) {
        Node node = findNodeByName(name);
        if (node != null) {
            System.out.println(name + "'s income is: " + node.income);
            System.out.println(name + "'s savings are: " + node.savings);
            for (Comment comment : node.comments) {
                System.out.println("Comment: " + comment.text + " (Date: " + comment.date + ", Expense: $" + comment.expense + ")");
            }
        } else {
            System.out.println("User not found.");
        }
    }

    
    public void searchExpensesByDate(String date) {
        Node temp = head;
        boolean found = false;
        while (temp != null) {
            for (Comment comment : temp.comments) {
                if (comment.date.equals(date)) {
                    System.out.println("Name: " + temp.name + ", Income: " + temp.income + ", Savings: " + temp.savings);
                    System.out.println("  Comment: " + comment.text + " (Date: " + comment.date + ", Expense: $" + comment.expense + ")");
                    found = true;
                }
            }
            temp = temp.next;
        }
        if (!found) {
            System.out.println("No expenses found for the specified date.");
        }
    }

    
    public void searchExpensesByValue(double value) {
        Node temp = head;
        boolean found = false;
        while (temp != null) {
            for (Comment comment : temp.comments) {
                if (comment.expense >= value) {
                    System.out.println("Name: " + temp.name + ", Income: " + temp.income + ", Savings: " + temp.savings);
                    System.out.println("  Comment: " + comment.text + " (Date: " + comment.date + ", Expense: $" + comment.expense + ")");
                    found = true;
                }
            }
            temp = temp.next;
        }
        if (!found) {
            System.out.println("No expenses found with the specified value.");
        }
    }
}

public class ExpenseManagementCLI {

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
                list.sortExpensesByDate();
                break;
            case "value":
                list.sortExpensesByValue();
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

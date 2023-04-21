package contacts;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    Database database;
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Main main;
        if (args.length > 0) {
            main = new Main(args[1]);
        } else {
            main = new Main();
        }
        main.showMenu();
    }
    Main() {
        database = new Database();
    }
    Main(String fileName) {
        database = new Database(fileName);
    }

    private void showMenu() {
        System.out.println("[menu] Enter action (add, list, search, count, exit)");
        String choice = scanner.nextLine();
        switch (choice) {
            case "add" -> addContact();
            case "list" -> listAllContacts();
            case "search" -> searchForRecord();
            case "count" -> countContacts();
            case "exit" -> {
                database.saveDb();
                System.exit(0);
            }
            default -> showMenu();
        }
    }

    private void searchForRecord() {
        System.out.println("Enter search query: ");
        String searchQuery = scanner.nextLine();
        List<Contact> foundContacts = new ArrayList<>();
        Pattern pattern = Pattern.compile(searchQuery, Pattern.CASE_INSENSITIVE);
        for (Contact contact: database.contactList
             ) {
            Matcher matcher = pattern.matcher(contact.getAllInfoForSearch());
            if (matcher.find()) {
                foundContacts.add(contact);
            }
        }
        printResults(foundContacts);
    }

    private void printResults(List<Contact> foundContacts) {
        System.out.println("Found " + foundContacts.size() + " results: ");
        for (int i = 0; i < foundContacts.size(); i++) {
            System.out.println(i+1 + ". " + foundContacts.get(i).getListing());
        }
        System.out.println("\n[search] Enter action ([number], back, again): ");
        String choice = scanner.nextLine();
        switch (choice) {
            case "back" -> showMenu();
            case "again" -> searchForRecord();
            default -> {
                int listNumber = Integer.parseInt(choice) - 1;
                int index = database.contactList.indexOf(foundContacts.get(listNumber));
                Contact chosenContact = database.contactList.get(index);
                System.out.println(chosenContact.getInfo());
                chooseAction(chosenContact);
            }
        }
    }

    private void chooseAction(Contact contact) {
        System.out.println("[record] Enter action (edit, delete, menu): ");
        String choice = scanner.nextLine();
        switch (choice) {
            case "edit" -> editContact(contact);
            case "delete" -> deleteContact(contact);
            case "menu" -> showMenu();
            default -> chooseAction(contact);
        }
    }

    private void deleteContact(Contact contact) {
        try {
            database.contactList.remove(contact);
            System.out.println("The record removed!\n");
        } catch (Exception e) {
            System.out.println("Something went wrong!");
        } finally {
            chooseAction(contact);
        }
    }

    private void editContact(Contact contact) {
        int choice = database.contactList.indexOf(contact);
        if (contact.getClass() == PersonContact.class) {
            contact = new PersonContactCreator().editContact(contact);
        } else {
            contact = new OrgContactCreator().editContact(contact);
        }
        database.contactList.remove(choice);
        database.contactList.add(choice, contact);
        System.out.println("Saved");
        System.out.println(contact.getInfo());
        chooseAction(contact);
    }


    private void listAllContacts() {
        if (isContactsEmpty()) {
            System.out.println("No records to list!");
            showMenu();
        } else {
            for (int i = 0; i < database.contactList.size(); i++) {
                System.out.println(i+1 + ". " + database.contactList.get(i).getListing());
            }
            System.out.println("\n[list] Enter action ([number], back): ");
            String choice = scanner.nextLine();
            if (choice.equals("back")) {
                showMenu();
            } else {
                int listNumber = Integer.parseInt(choice) - 1;
                Contact chosenContact = database.contactList.get(listNumber);
                System.out.println(chosenContact.getInfo());
                chooseAction(chosenContact);
            }
        }
    }

    private void countContacts() {
        System.out.println("The Phone Book has " + database.contactList.size() + " records.");
        showMenu();
    }

    private boolean isContactsEmpty() {
        return database.contactList.size() == 0;
    }

    private void addContact() {
        System.out.println("Enter the type (person, organization): ");
        String choice = scanner.nextLine();
        Contact newRecord = switch (choice) {
            case "person" -> new PersonContactCreator().createContact();
            case "organization" -> new OrgContactCreator().createContact();
            default -> null;
        };
        if (newRecord == null) {
            System.out.println("Something went wrong!");
            showMenu();
        }
        database.contactList.add(newRecord);
        System.out.println("The record added.\n");
        showMenu();
    }
}

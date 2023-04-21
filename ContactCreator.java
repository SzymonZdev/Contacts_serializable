package contacts;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

public interface ContactCreator {
    Contact createContact();
    Contact editContact(Contact contact);
    default boolean numberChecker(String number) {
        return number.matches("\\+?(((\\([0-9a-zA-Z]+\\)[- ][0-9a-zA-Z]{2,})|([0-9a-zA-Z]+[- ]\\([0-9a-zA-Z]{2,}\\)))|(\\(?[0-9a-zA-Z]*\\)?))([- ]([0-9a-zA-Z]{2,}[- ]?)*)?");
    }
}

class PersonContactCreator implements ContactCreator {
    private final Scanner scanner = new Scanner(System.in);
    private String name;
    private String number;
    private String surname;
    private LocalDate birthdate;
    private String gender;
    private void askForDetails() {
        this.name = askForName();
        this.surname = askForSurname();
        this.birthdate = askForBirthday();
        this.gender = askForGender();
        this.number = askForNumber();
    }

    private String askForGender() {
        System.out.println("Enter the gender (M, F): ");
        String choice = scanner.nextLine();
        if (choice.equals("M") || choice.equals("F")) {
            return choice;
        } else {
            System.out.println("Bad gender!");
            return null;
        }
    }

    private String askForNumber() {
        System.out.println("Enter the number: ");
        String number = scanner.nextLine();
        if (number.length() > 0 && numberChecker(number)) {
            return number;
        } else {
            System.out.println("Wrong number format!");
            return null;
        }
    }

    private LocalDate askForBirthday() {
        System.out.println("Enter the birth date: ");
        try {
            return LocalDate.parse(scanner.nextLine());
        } catch (DateTimeParseException e) {
            System.out.println("Bad birth date!");
            return null;
        }
    }

    private String askForSurname() {
        System.out.println("Enter the surname: ");
        return scanner.nextLine();
    }

    private String askForName() {
        System.out.println("Enter the name: ");
        return scanner.nextLine();
    }

    @Override
    public PersonContact createContact() {
        askForDetails();
        PersonContact personContact = new PersonContact(name, number, surname, birthdate, gender);
        personContact.createTime = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        personContact.editTime = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        return personContact;
    }
    @Override
    public PersonContact editContact(Contact contact) {
        System.out.println("Select a field (name, surname, birth, gender, number): ");
        String option = scanner.nextLine();
        switch (option) {
            case "name" -> contact.setName(askForName());
            case "surname" -> ((PersonContact) contact).setSurname(askForSurname());
            case "birth" -> ((PersonContact) contact).setBirthdate(askForBirthday());
            case "gender" -> ((PersonContact) contact).setGender(askForGender());
            case "number" -> contact.setNumber(askForNumber());
        }
        PersonContact personContact =  new PersonContact(contact.name, contact.number, ((PersonContact) contact).surname, ((PersonContact) contact).birthdate, ((PersonContact) contact).gender);
        personContact.createTime = contact.createTime;
        personContact.editTime = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        return personContact;
    }
}

class OrgContactCreator implements ContactCreator {
    private final Scanner scanner = new Scanner(System.in);
    private String name;
    private String address;
    private String number;
    private void askForDetails() {
        this.name = askForName();
        this.address = askForAddress();
        this.number = askForNumber();
    }

    private String askForNumber() {
        System.out.println("Enter the number: ");
        String number = scanner.nextLine();
        if (number.length() > 0 && numberChecker(number)) {
            return  number;
        } else {
            System.out.println("Wrong number format!");
            return null;
        }
    }
    private String askForName() {
        System.out.println("Enter the organization name: ");
        return scanner.nextLine();
    }
    private String askForAddress() {
        System.out.println("Enter the address: ");
        return scanner.nextLine();
    }
    @Override
    public Contact createContact() {
        askForDetails();
        OrgContact orgContact = new OrgContact(name, number, address);
        orgContact.createTime = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        orgContact.editTime = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        return orgContact;
    }

    @Override
    public OrgContact editContact(Contact contact) {
        System.out.println("Select a field (address, number): ");
        String option = scanner.nextLine();
        switch (option) {
            case "address" -> ((OrgContact)contact).setAddress(askForAddress());
            case "number" -> contact.setNumber(askForNumber());
        }
        OrgContact orgContact = new OrgContact(contact.name, contact.number, ((OrgContact) contact).getAddress());
        orgContact.createTime = contact.createTime;
        orgContact.editTime = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        return orgContact;
    }
}

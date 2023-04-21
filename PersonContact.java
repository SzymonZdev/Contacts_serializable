package contacts;

import java.time.LocalDate;

public class PersonContact extends Contact {
    String surname;
    LocalDate birthdate;
    String gender;

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public PersonContact(String name, String number, String surname, LocalDate birthdate, String gender) {
        super(name, number);
        this.surname = surname;
        this.birthdate = birthdate;
        this.gender = gender;
    }

    @Override
    public String getListing() {
        return super.name + " " + this.surname;
    }
    @Override
    public String getInfo() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Name: ").append(this.getName()).append("\nSurname: ").append(this.getSurname()).append("\nBirth date: ");
        if (this.getBirthdate() == null) {
            stringBuilder.append("[no data]");
        } else {
            stringBuilder.append(this.getBirthdate());
        }
        stringBuilder.append("\nGender: ");
        if (this.getGender() == null) {
            stringBuilder.append("[no data]");
        } else {
            stringBuilder.append(this.getGender());
        }
        stringBuilder.append("\nNumber: ");
        if (this.getNumber() == null) {
            stringBuilder.append("[no data]");
        } else {
            stringBuilder.append(this.getNumber());
        }
        stringBuilder.append("\nTime created: ").append(this.createTime).append("\nTime last edit: ").append(this.editTime).append("\n");

        return stringBuilder.toString();
    }

    @Override
    public String getAllInfoForSearch() {
        return this.name + this.surname + this.gender + this.birthdate + this.number;
    }
}
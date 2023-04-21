package contacts;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
    public List<Contact> contactList;
    boolean hasFile = false;
    String fileName;


    public Database() {
        this.contactList = initializeDb();
    }
    public Database(String fileName) {
        this.contactList = initializeDb(fileName);
    }

    private List<Contact> initializeDb() {
        return new ArrayList<>();
    }
    private List<Contact> initializeDb(String fileName) {
        hasFile = true;
        this.fileName = fileName;
        try (FileInputStream fileInputStream = new FileInputStream(System.getProperty("user.dir") + "\\" + fileName);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            return (List<Contact>) objectInputStream.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return new ArrayList<>();
    }


    public void saveDb() {
        if (!hasFile) {
            try (FileOutputStream fileOutputStream = new FileOutputStream(System.getProperty("user.dir") + "\\database.db");
                 ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
                objectOutputStream.writeObject(contactList);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try (FileOutputStream fileOutputStream = new FileOutputStream(System.getProperty("user.dir") + "\\" + this.fileName);
                 ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
                objectOutputStream.writeObject(contactList);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}


package contacts;


import java.io.Serializable;
import java.time.LocalDateTime;

abstract class Contact implements Serializable {
    protected String name;
    protected String number;
    protected LocalDateTime createTime;
    protected LocalDateTime editTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public abstract String getListing();
    public abstract String getInfo();
    public abstract String getAllInfoForSearch();

    public Contact(String name, String number) {
        this.name = name;
        this.number = number;
    }
}



package contacts;

public class OrgContact extends Contact{
    String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public OrgContact(String name, String number, String address) {
        super(name, number);
        this.address = address;
    }
    @Override
    public String getListing() {
        return super.name;
    }
    @Override
    public String getInfo() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Organization name: ").append(this.getName()).append("\nAddress: ").append(this.getAddress()).append("\nNumber: ");
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
        return this.name + this.address + this.number;
    }
}

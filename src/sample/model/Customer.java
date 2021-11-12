package sample.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.Serializable;

public class Customer implements Serializable {
    private int customerID;
    private final transient StringProperty firstName;
    private final transient StringProperty lastName;
    private final transient StringProperty address;
    private final transient StringProperty email;
    private final transient StringProperty phone;
    static int customerIDcount = 0;

    // CONSTRUCTOR
    public Customer(String firstName, String lastName, String address, String email, String phone) {
        this.customerID = customerIDcount;
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.address = new SimpleStringProperty(address);
        this.email = new SimpleStringProperty(email);
        this.phone = new SimpleStringProperty(phone);
        customerIDcount++;
    }

    // GET
    public int getCustomerID() {
        return customerID;
    }

    public String getFirstName() {
        return firstName.get();
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public String getLastName() {
        return lastName.get();
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public String getAddress() {
        return address.get();
    }

    public StringProperty addressProperty() {
        return address;
    }

    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public String getPhone() {
        return phone.get();
    }

    public StringProperty phoneProperty() {
        return phone;
    }

    public static int getCount() {
        return customerIDcount;
    }

    // SET
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }

    public static void setCustomerIDcount(int customerIDcount) {
        Customer.customerIDcount = customerIDcount;
    }

    @Override
    public String toString(){
        return "CustomerID: " + customerID+ "FirstName: "+ firstName+ "LastName: "+lastName+ "Adress: "+ address + "Email: "+ email+ "Phone: "+phone;
    }
}

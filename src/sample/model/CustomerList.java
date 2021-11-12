package sample.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;

public class CustomerList {
    private ObservableList<Customer> customerList = FXCollections.observableArrayList();

    // CONSTRUCTOR
    public CustomerList() {
    }

    // GETTER
    public ObservableList<Customer> getCustomerList() {
        return this.customerList;
    }

    public Customer getCustomer(int pos) {
        return this.customerList.get(pos);
    }

    // METHOD
    public void addCustomer(Customer c) {
        this.customerList.add(c);
    }

    public void removeCustomer(int pos) {
        this.customerList.remove(pos);
    }

    public void editCustomer(int pos, Customer c) {
        this.customerList.get(pos).setFirstName(c.getFirstName());
        this.customerList.get(pos).setLastName(c.getLastName());
        this.customerList.get(pos).setAddress(c.getAddress());
        this.customerList.get(pos).setEmail(c.getEmail());
        this.customerList.get(pos).setPhone(c.getPhone());
    }

    public boolean saveToFile() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("src/sample/customers.dat"));
            out.writeInt(this.customerList.size());
            for (Customer customer : this.customerList) {
                out.writeInt(customer.getCustomerID());
                out.writeUTF(customer.getFirstName());
                out.writeUTF(customer.getLastName());
                out.writeUTF(customer.getAddress());
                out.writeUTF(customer.getEmail());
                out.writeUTF(customer.getPhone());
            }

            out.close();
        } catch (IOException exception) {
            exception.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean loadFromFile() {
        try {
            this.customerList = FXCollections.observableArrayList();

            ObjectInputStream in = new ObjectInputStream(new FileInputStream("src/sample/customers.dat"));
            int size = in.readInt();
            for (int i = 0; i < size; i++) {
                Customer customer = new Customer(null, null, null, null, null);

                customer.setCustomerID(in.readInt());
                customer.setFirstName(in.readUTF());
                customer.setLastName(in.readUTF());
                customer.setAddress(in.readUTF());
                customer.setEmail(in.readUTF());
                customer.setPhone(in.readUTF());

                this.customerList.add(customer);
            }

            if (size > 0)
                Customer.setCustomerIDcount(this.customerList.get(size - 1).getCustomerID() + 1);
            in.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }
}

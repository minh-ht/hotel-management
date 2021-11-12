package sample.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.controller.CustomerSceneController;

import java.io.*;
import java.time.LocalDate;

public class Hotel {
    private String name;
    private String address;
    private ObservableList<Room> roomList = FXCollections.observableArrayList();

    // CONSTRUCTOR
    public Hotel() {
        try {
            loadFromFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Hotel(String name, String address) {
        this.name = name;
        this.address = address;
    }

    // GETTER
    public String getName() {
        return this.name;
    }

    public String getAddress() {
        return this.address;
    }

    public ObservableList<Room> getRoomList() {
        return roomList;
    }

    public Room getRoom(int pos) {
        return this.roomList.get(pos);
    }

    //SET
    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    // METHOD
    public void addRoom(Room newRoom) {
        this.roomList.add(newRoom);
    }

    public void removeRoom(int pos) {
        this.roomList.remove(pos);
    }

    public void removeRoom(Room room) {this.roomList.remove(room);}

    public void editRoom(int pos, Room newRoom) {
        Room room = this.getRoom(pos);
        room.setPrice(newRoom.getPrice());
        room.setType(newRoom.getType());
    }

    public void reserve(int pos, Customer c, LocalDate dateTo, LocalDate dateFrom) {
        Room room = this.getRoom(pos);
        room.setStatus("Reserved");
        room.setOccupant(c);
        room.setDateTo(dateTo);
        room.setDateFrom(dateFrom);
    }

    public boolean saveToFile() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("src/sample/hotel.dat"));
            out.writeObject(this.name);
            out.writeObject(this.address);
            out.writeInt(this.roomList.size());
            for (Room room : this.roomList) {
                out.writeInt(room.getRoomIDint());
                out.writeUTF(room.getRoomID());
                out.writeUTF(room.getType());
                out.writeDouble((room.getPrice()));
                out.writeUTF(room.statusProperty().getValueSafe());

                out.writeObject(room.getOccupant());

                out.writeObject(room.getDateFrom());
                out.writeObject(room.getDateTo());
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
            this.roomList = FXCollections.observableArrayList();

            ObjectInputStream in = new ObjectInputStream(new FileInputStream("src/sample/hotel.dat"));

            String savedName = (String) in.readObject();
            String savedAddress = (String) in.readObject();
            this.setName(savedName);
            this.setAddress(savedAddress);

            int size = in.readInt();
            for (int i = 0; i < size; i++) {
                Room room = new Room();
                room.setRoomIDint(in.readInt());
                room.setRoomID(in.readUTF());
                room.setType(in.readUTF());
                room.setPrice(in.readDouble());
                room.setStatus(in.readUTF());

                room.setOccupant((Customer) in.readObject());
                if (room.getOccupant() != null) {
                    for (Customer customer : CustomerSceneController.getCustomerList().getCustomerList()) {
                        if (room.getOccupant().getCustomerID() == customer.getCustomerID()) {
                            room.getOccupant().setFirstName(customer.getFirstName());
                            room.getOccupant().setLastName(customer.getLastName());
                            room.getOccupant().setAddress(customer.getEmail());
                            room.getOccupant().setEmail(customer.getEmail());
                            room.getOccupant().setPhone(customer.getPhone());
                            break;
                        }
                    };
                }

                room.setDateFrom((LocalDate) in.readObject());
                room.setDateTo((LocalDate) in.readObject());

                roomList.add(room);
            }

            if (size > 0)
                Room.setRoomIDcount(this.roomList.get(size - 1).getRoomIDint() + 1);
            in.close();
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }
}

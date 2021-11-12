package sample.model;

import javafx.beans.property.*;

import java.io.Serializable;
import java.time.LocalDate;

public class Room implements Serializable {
    private int roomIDint;
    private String roomID;
    private String type;
    private double price;
    private transient StringProperty status;
    private Customer occupant;
    private LocalDate dateTo;
    private LocalDate dateFrom;

    static int roomIDcount;

    // CONSTRUCTOR
    public Room(String type, double price) {
        this(type, price, "Available", null, null, null);
    }

    public Room(String type, double price, String status, Customer occupant, LocalDate dateTo, LocalDate dateFrom) {
        this.roomIDint = roomIDcount;
        this.roomID = roomIDcal(roomIDcount);
        this.type = type;
        this.price = price;
        this.status = new SimpleStringProperty(status);
        this.occupant = occupant;
        this.dateTo = LocalDate.of(2000,1,1);
        this.dateFrom = LocalDate.of(2000,1,1);
        roomIDcount++;
    }

    public Room() {
        this("Single", 0);
    }

    // GET
    public int getRoomIDint() {
        return roomIDint;
    }

    public String getRoomID() {
        return roomID;
    }

    public String getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }

    public String getStatus() {
        return status.get();
    }

    public StringProperty statusProperty() {
        return status;
    }

    public Customer getOccupant() {
        return occupant;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public static int getRoomIDcount() {
        return roomIDcount;
    }

    // SET

    public void setRoomIDint(int roomIDint) {
        this.roomIDint = roomIDint;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public void setOccupant(Customer occupant) {
        this.occupant = occupant;
    }

    public void setDateTo(LocalDate dateTo) {
        this.dateTo = dateTo;
    }

    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    public static void setRoomIDcount(int roomIDcount) {
        Room.roomIDcount = roomIDcount;
    }

    // METHOD
    private String roomIDcal(int n) {
        return Integer.toString(n/10 + 1) + '0' + Integer.toString(n%10);
    }

    public boolean isAvailable() {
        return this.status.getValue().equals("Available");
    }

    public boolean isMaintaining() {
        return this.status.getValue().equals("Maintaining");
    }

    public boolean isReserved() {
        return this.status.getValue().equals("Reserved");
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomIDint=" + roomIDint +
                ", roomID=" + roomID +
                ", type=" + type +
                ", price=" + price +
                ", status=" + status +
                ", occupant=" + occupant +
                ", dateTo=" + dateTo +
                ", dateFrom=" + dateFrom +
                '}';
    }
}
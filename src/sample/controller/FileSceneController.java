package sample.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import sample.model.Customer;
import sample.model.CustomerList;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FileSceneController implements Initializable {
    @FXML
    private Button loadBtn;

    @FXML
    private Button saveBtn;

    private RoomSceneController roomController;
    private CustomerSceneController customerController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void loadClicked() {
        CustomerSceneController.getCustomerList().loadFromFile();
        RoomSceneController.getHotel().loadFromFile();

        customerController.init();
        roomController.init();
    }

    public void saveClicked() {
        CustomerSceneController.getCustomerList().saveToFile();
        RoomSceneController.getHotel().saveToFile();
    }

    public void setController(RoomSceneController roomController, CustomerSceneController customerController) {
        this.roomController = roomController;
        this.customerController = customerController;
    }
}

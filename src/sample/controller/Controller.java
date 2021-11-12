package sample.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import sample.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private AnchorPane main;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private Button fileBtn;

    @FXML
    private Button roomBtn;

    @FXML
    private Button customerBtn;

    @FXML
    private Button aboutBtn;

    private final FXMLLoader fileLoader;
    private final FXMLLoader roomLoader;
    private final FXMLLoader customerLoader;
    private Parent fileRoot;
    private Parent roomRoot;
    private Parent customerRoot;

    public Controller() throws IOException {
        fileLoader = new FXMLLoader();
        fileLoader.setLocation(Main.class.getResource("fxml/fileScene.fxml"));
        fileRoot = fileLoader.load();

        roomLoader = new FXMLLoader();
        roomLoader.setLocation(Main.class.getResource("fxml/roomScene.fxml"));
        roomRoot = roomLoader.load();

        customerLoader = new FXMLLoader();
        customerLoader.setLocation(Main.class.getResource("fxml/customerScene.fxml"));
        customerRoot = customerLoader.load();

        FileSceneController fileController = fileLoader.getController();
        RoomSceneController roomController = roomLoader.getController();
        CustomerSceneController customerController = customerLoader.getController();
        fileController.setController(roomController, customerController);
        roomController.setController(customerController);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void changeToFile(MouseEvent event) throws IOException {
        mainPane.getChildren().setAll(fileRoot);
    }

    public void changeToRoom(MouseEvent event) throws IOException {
        mainPane.getChildren().setAll(roomRoot);
    }

    public void changeToCustomer(MouseEvent event) throws IOException {
        mainPane.getChildren().setAll(customerRoot);
    }
}

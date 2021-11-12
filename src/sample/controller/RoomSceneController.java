package sample.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.TilePane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import sample.Main;
import sample.model.Hotel;
import sample.model.Room;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class RoomSceneController implements Initializable {
    @FXML
    private Button addBtn;

    @FXML
    private TilePane tilePane;

    private static final Hotel hotel = new Hotel("Nirvana Millennium", "Hanoi");
    private static Map<Button, Room> button2room;
    private static Map<Room, Button> room2button;
    private CustomerSceneController customerController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        init();
    }

    // GET
    public static Hotel getHotel() {
        return hotel;
    }

    public static Map<Button, Room> getButton2room() {
        return button2room;
    }

    public static Map<Room, Button> getRoom2Button() {
        return room2button;
    }

    // SET
    public void setController(CustomerSceneController customerController) {
        this.customerController = customerController;
    }

    // METHOD
    public void init() {
        button2room = new HashMap<>();
        room2button = new HashMap<>();
        tilePane.getChildren().clear();
        for (Room room : hotel.getRoomList()) {
            if (room.getStatus().equals("Reserved"))
                if (room.getDateFrom().isBefore(LocalDate.now()) && room.getDateTo().isAfter(LocalDate.now()))
                    room.setStatus("Occupied");
                else if (room.getDateTo().isBefore(LocalDate.now()))
                    room.setStatus("Available");
            addButtonToPane(room);
        }
    }

    public void addButtonClicked() throws IOException {
        Room room = new Room();
        if (openRoomEditDialog(room, tilePane.getScene().getWindow())) {
            hotel.addRoom(room);
            addButtonToPane(room);
        }
    }

    private void addButtonToPane(Room room) {
        Button button = createRoomButton(room);
        button2room.put(button, room);
        room2button.put(room, button);
        room.statusProperty().addListener((observable, oldValue, newValue) -> {
            changeStatusColor(room2button.get(room), newValue);
        });
        tilePane.getChildren().add(button);
    }

    public static boolean openRoomEditDialog(Room room, Window owner) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("fxml/roomEditDialog.fxml"));
        Parent root = loader.load();

        Stage dialogStage = new Stage();
        dialogStage.setTitle("Edit Room");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(owner);
        Scene scene = new Scene(root);
        dialogStage.setScene(scene);

        RoomEditDialogController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setRoom(room);

        dialogStage.showAndWait();
        return controller.isOkClicked();
    }

    public static void openRoomDialog(Room room,
                                      Window owner,
                                      TilePane tile) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("fxml/roomDialog.fxml"));
        Parent root = loader.load();

        Stage dialogStage = new Stage();
        dialogStage.setTitle("Room information");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(owner);
        Scene scene = new Scene(root);
        dialogStage.setScene(scene);

        RoomDialogController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setParentTilePane(tile);
        controller.setRoom(room);

        dialogStage.showAndWait();
    }

    private Button createRoomButton(Room room) {
        Button newBtn = new Button();
        newBtn.setText(room.getRoomID());
        newBtn.setPrefSize(60, 25);

        changeStatusColor(newBtn, room.getStatus());

        // Open room window when clicked on room
        newBtn.setOnMouseClicked((e) -> {
            Room currentRoom = button2room.get((Button) e.getSource());
            try {
                openRoomDialog(currentRoom, tilePane.getScene().getWindow(), this.tilePane);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        return newBtn;
    }

    private void changeStatusColor(Button button, String status) {
        String style = "-fx-background-color: #98F79A;";
        switch (status) {
            case "Available":
                style = "-fx-background-color: #98F79A;";
                break;
            case "Maintaining":
                style = "-fx-background-color: #F9BB92;";
                break;
            case "Reserved":
                style = "-fx-background-color: #9DB0F0;";
                break;
            case "Occupied":
                style = "-fx-background-color: #F69B97;";
                break;
            default:
                break;
        }
        button.setStyle(style);
    }
}

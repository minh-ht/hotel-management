package sample.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import sample.Main;
import sample.model.Customer;
import sample.model.Room;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Map;
import java.util.ResourceBundle;

public class RoomDialogController implements Initializable {
    @FXML
    private Text num;

    @FXML
    private Text roomID;

    @FXML
    private Text roomPrice;

    @FXML
    private Text roomType;

    @FXML
    private Text roomStatus;

    @FXML
    private Button editBtn;

    @FXML
    private Button cancelBtn;

    @FXML
    private Button deleteBtn;

    @FXML
    private Button reserveBtn;

    @FXML
    private Button checkOutBtn;

    @FXML
    private Pane pane;

    private Stage dialogStage;
    private TilePane tilePane;
    private Room room;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
        this.dialogStage.setResizable(false);
    }

    public void setParentTilePane(TilePane tile) {
        this.tilePane = tile;
    }

    public void setRoom(Room room) throws IOException {
        this.room = room;

        num.setText(this.room.getRoomID());
        roomID.setText(this.room.getRoomID());
        roomPrice.setText(String.valueOf(this.room.getPrice()));
        roomType.setText(this.room.getType());
        roomStatus.setText(this.room.getStatus());

        if (room.getStatus().equals("Occupied") || room.getStatus().equals("Reserved")) {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("fxml/occupantPane.fxml"));
            Parent infoRoot = loader.load();
            OccupantPane controller = loader.getController();
            System.out.println(this.room.getOccupant());
            controller.setTextField(this.room);

            toggleButton();

            pane.getChildren().setAll(infoRoot);
        }
    }

    public static boolean openFindCustomerDialog(Room room,
                                                 Window owner) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("fxml/findCustomerDialog.fxml"));
        Parent root = loader.load();

        Stage dialogStage = new Stage();
        dialogStage.setTitle("Find customer");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(owner);
        Scene scene = new Scene(root);
        dialogStage.setScene(scene);

        FindCustomerDialogController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setRoom(room);

        dialogStage.showAndWait();
        return controller.isOkClicked();
    }

    @FXML
    public void handleEditButton() throws IOException {
        if (RoomSceneController.openRoomEditDialog(room, dialogStage))
            setRoom(room);
    }

    @FXML
    private void handleCancelButton() {
        this.dialogStage.close();
    }

    @FXML
    private void handleDeleteButton() throws IOException {
        if (DeleteDialogController.openDeleteDialog(this.room, this.dialogStage.getOwner())) {
            Map<Room, Button> room2button = RoomSceneController.getRoom2Button();
            Map<Button, Room> button2room = RoomSceneController.getButton2room();
            tilePane.getChildren().remove(room2button.get(room));
            button2room.remove(room2button.get(room));
            room2button.remove(room);
            RoomSceneController.getHotel().removeRoom(room);
            this.dialogStage.close();
        }
    }

    @FXML
    private void handleReserveButton() throws IOException {
        if (openFindCustomerDialog(this.room, this.pane.getScene().getWindow())) {
            this.room.setStatus("Reserved");

            FXMLLoader loader = new FXMLLoader(Main.class.getResource("fxml/occupantPane.fxml"));
            Parent infoRoot = loader.load();
            OccupantPane controller = loader.getController();
            controller.setTextField(this.room);

            toggleButton();

            pane.getChildren().clear();
            pane.getChildren().setAll(infoRoot);
            roomStatus.setText(this.room.getStatus());
        }
    }

    @FXML
    private void handleCheckOutButton() {
        this.room.setOccupant(null);
        this.room.setStatus("Available");
        this.room.setDateFrom(null);
        this.room.setDateTo(null);

        reserveBtn.setVisible(true);
        reserveBtn.setDisable(false);

        checkOutBtn.setVisible(false);
        checkOutBtn.setDisable(true);
        ImageView imageView = new ImageView(Main.class.getResource("resources/assests/img/placeholder_img.jpg").toString());
        pane.getChildren().clear();
    }

    private void toggleButton() {
        reserveBtn.setVisible(false);
        reserveBtn.setDisable(true);

        checkOutBtn.setVisible(true);
        checkOutBtn.setDisable(false);
    }
}

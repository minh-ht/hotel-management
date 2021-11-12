package sample.controller;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.model.Room;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RoomEditDialogController implements Initializable {
    @FXML
    private Button okBtn;

    @FXML
    private Button cancelBtn;

    @FXML
    private TextField roomID;

    @FXML
    private TextField roomPrice;

    @FXML
    private ChoiceBox<String> roomType;

    @FXML
    private ChoiceBox<String> roomStatus;

    private Stage dialogStage;
    private Room room;
    private boolean okClicked = false;
    private final ObservableList<String> typeOptions = FXCollections.observableArrayList("Single", "Double", "Triple");
    private final ObservableList<String> statusOptions = FXCollections.observableArrayList("Available",
                                                                                                "Maintaining",
                                                                                                "Reserved");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        roomType.setItems(typeOptions);
        roomType.setValue(typeOptions.get(0));

        roomStatus.setItems(statusOptions);
        roomStatus.setValue("Available");
        roomStatus.getSelectionModel().selectedIndexProperty().addListener(
                (ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
                    String newStatus = statusOptions.get(new_val.intValue());
                    if (newStatus.equals("Reserved")) {
                        try {
                            if (RoomDialogController.openFindCustomerDialog(this.room, this.dialogStage.getOwner())) {
                                this.room.setStatus("Reserved");
                            }
                            else {
                                roomStatus.setValue(newStatus);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
        dialogStage.setResizable(false);
    }

    public void setRoom(Room room) {
        this.room = room;
        roomID.setText(this.room.getRoomID());
        roomPrice.setText(String.valueOf(this.room.getPrice()));
        roomType.setValue(this.room.getType());
        roomStatus.setValue(this.room.getStatus());
    }

    @FXML
    private void handleOK() {
        if (isInputValid()) {
            this.room.setRoomID(roomID.getText());
            this.room.setPrice(Double.parseDouble(roomPrice.getText()));
            this.room.setType(roomType.getValue());
            this.room.setStatus(roomStatus.getValue());

            okClicked = true;
            dialogStage.close();
        }
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (roomPrice.getText() == null || roomPrice.getText().length() == 0) {
            errorMessage += "Price field doesn't have a value.\n";
        }
        else {
            try {
                double dField = Double.parseDouble(roomPrice.getText());
            } catch (NumberFormatException ex) {
                errorMessage += "Price must be a number!\n";
            }
        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }

    public boolean isOkClicked() {
        return okClicked;
    }
}

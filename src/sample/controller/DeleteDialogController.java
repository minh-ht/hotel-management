package sample.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import sample.Main;
import sample.model.Room;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DeleteDialogController implements Initializable {
    @FXML
    private Text text;

    @FXML
    private Button OkBtn;

    @FXML
    private Button cancelBtn;

    private Stage dialogStage;
    private Object object;
    private boolean okClicked = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    // GET
    public boolean isOkClicked() {
        return this.okClicked;
    }

    // SET
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
        this.dialogStage.setResizable(false);
    }

    public void setObject(Object object) {
        this.object = object;
        text.setText(text.getText() + (object.getClass().equals(Room.class) ?
                                        " this room?" :
                                        " this customer?"));
    }

    // METHOD
    @FXML
    private void handleOK() {
        okClicked = true;
        dialogStage.close();
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    public static boolean openDeleteDialog(Object object, Window owner) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("fxml/deleteDialog.fxml"));
        Parent root = loader.load();

        Stage dialogStage = new Stage();
        dialogStage.setTitle(object.getClass().equals(Room.class) ?
                                "Delete room" :
                                "Delete customer");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(owner);
        Scene scene = new Scene(root);
        dialogStage.setScene(scene);

        DeleteDialogController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setObject(object);

        dialogStage.showAndWait();
        return controller.isOkClicked();
    }
}

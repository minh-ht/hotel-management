package sample.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import sample.model.Customer;
import sample.model.Room;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class OccupantPane implements Initializable {
    @FXML
    private Text firstName;

    @FXML
    private Text phone;

    @FXML
    private Text dateFrom;

    @FXML
    private Text dateTo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void setTextField(Room room) {
        firstName.setText(room.getOccupant().getFirstName());
        phone.setText(room.getOccupant().getPhone());
        dateFrom.setText(room.getDateFrom().format(DateTimeFormatter.ofPattern("dd/MM/yy")));
        dateTo.setText(room.getDateFrom().format(DateTimeFormatter.ofPattern("dd/MM/yy")));
    }
}

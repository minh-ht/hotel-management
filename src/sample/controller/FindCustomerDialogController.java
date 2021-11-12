package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.model.Customer;
import sample.model.CustomerList;
import sample.model.Room;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class FindCustomerDialogController implements Initializable {
    @FXML
    private TableView<Customer> table;

    @FXML
    private TableColumn<Customer, String> firstName;

    @FXML
    private TableColumn<Customer, String> lastName;

    @FXML
    private TableColumn<Customer, String> address;

    @FXML
    private TableColumn<Customer, String> email;

    @FXML
    private TableColumn<Customer, String> phone;

    @FXML
    private Button addBtn;

    @FXML
    private Button okBtn;

    @FXML
    private Button cancelBtn;

    @FXML
    private TextField searchText;

    @FXML
    private DatePicker dateFrom;

    @FXML
    private DatePicker dateTo;

    private Stage dialogStage;
    private Room room;
    private boolean okClicked = false;

    private FilteredList<Customer> filteredData;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        firstName.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        lastName.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        address.setCellValueFactory(cellData -> cellData.getValue().addressProperty());
        email.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        phone.setCellValueFactory(cellData -> cellData.getValue().phoneProperty());

        firstName.setSortType(TableColumn.SortType.ASCENDING);
        lastName.setSortable(false);

        this.filteredData = new FilteredList<Customer>(CustomerSceneController.getCustomerList().getCustomerList());
        table.setItems(filteredData);

        searchText.textProperty().addListener((observable, oldValue, newValue) -> {
            String filter = searchText.getText();
            if (filter == null || filter.length() == 0)
                filteredData.setPredicate(customer -> true);
            else {
                filteredData.setPredicate(customer -> searchFindsCustomer(customer, searchText.getText()));
            }
        });
    }

    private boolean searchFindsCustomer(Customer customer, String searchText) {
        return ( customer.getFirstName().toLowerCase().contains(searchText.toLowerCase())) ||
                (customer.getLastName().toLowerCase().contains(searchText.toLowerCase())) ||
                (customer.getPhone().toLowerCase().contains(searchText.toLowerCase()));
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
        this.dialogStage.setResizable(false);
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (dateFrom.getValue() == null || dateTo.getValue() == null) {
            if (dateFrom.getValue() == null)
                errorMessage += "Check-in date hasn't been filled.\n";
            if (dateTo.getValue() == null)
                errorMessage += "Check-out date hasn't been filled.\n";
        }
        else {
            if (dateFrom.getValue().isAfter(dateTo.getValue()))
                errorMessage += "Check-in date can't be after check-out date.\n";
            if (dateFrom.getValue().isBefore(LocalDate.now()))
                errorMessage += "Check-in date can't be before today.\n";
        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(this.dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }

    public boolean isOkClicked() {
        return this.okClicked;
    }

    @FXML
    private void handleAddButton() throws IOException {
        Customer tempCustomer = new Customer("firstname",
                                             "lastname",
                                              "address",
                                                "email",
                                               "phone");
        if (CustomerSceneController.openCustomerAddDialog(tempCustomer, table.getScene().getWindow()))
            CustomerSceneController.getCustomerList().addCustomer(tempCustomer);
    }

    @FXML
    private void handleCancelButton() {
        this.dialogStage.close();
    }

    @FXML
    private void handleOkButton() {
        Customer selected = table.getSelectionModel().getSelectedItem();
        if (selected != null && isInputValid()) {
            this.room.setOccupant(selected);

            this.room.setDateFrom(dateFrom.getValue());
            this.room.setDateTo(dateTo.getValue());

            okClicked = true;
            dialogStage.close();
        }
    }
}

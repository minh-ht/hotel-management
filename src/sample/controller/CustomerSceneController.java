package sample.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import sample.Main;
import sample.model.Customer;
import sample.model.CustomerList;
import sample.model.Room;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class CustomerSceneController implements Initializable {
    @FXML
    private TableView<Customer> table;

    @FXML
    private Button addBtn;

    @FXML
    private Button deleteBtn;

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

    public static final CustomerList customerList = new CustomerList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        firstName.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        lastName.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        address.setCellValueFactory(cellData -> cellData.getValue().addressProperty());
        email.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        phone.setCellValueFactory(cellData -> cellData.getValue().phoneProperty());

        firstName.setSortType(TableColumn.SortType.ASCENDING);
        lastName.setSortable(false);

        table.setItems(customerList.getCustomerList());

        firstName.setCellFactory(TextFieldTableCell.forTableColumn());
        firstName.setOnEditCommit(
                event -> {
                    Customer customer = event.getTableView().getItems().get(event.getTablePosition().getRow());
                    String newValue = event.getNewValue();
                    String errorMessage = "";
                    if (newValue == null || newValue.length() == 0) {
                        errorMessage += "Not valid first name!\n";
                    }
                    if (errorMessage.length() == 0) {
                        customer.setFirstName(newValue);
                    } else {
                        showError(errorMessage);
                    }
                }
        );

        lastName.setCellFactory(TextFieldTableCell.forTableColumn());
        lastName.setOnEditCommit(
                event -> {
                    Customer customer = event.getTableView().getItems().get(event.getTablePosition().getRow());
                    String newValue = event.getNewValue();
                    String errorMessage = "";
                    if (newValue == null || newValue.length() == 0) {
                        errorMessage += "Not valid last name!\n";
                    }
                    if (errorMessage.length() == 0) {
                        customer.setLastName(newValue);
                    } else {
                        showError(errorMessage);
                    }
                }
        );

        address.setCellFactory(TextFieldTableCell.forTableColumn());
        address.setOnEditCommit(
                event -> {
                    Customer customer = event.getTableView().getItems().get(event.getTablePosition().getRow());
                    String newValue = event.getNewValue();
                    String errorMessage = "";
                    if (newValue == null || newValue.length() == 0) {
                        errorMessage += "Not valid address!\n";
                    }
                    if (errorMessage.length() == 0) {
                        customer.setAddress(newValue);
                    } else {
                        showError(errorMessage);
                    }
                }
        );

        email.setCellFactory(TextFieldTableCell.forTableColumn());
        email.setOnEditCommit(
                event -> {
                    Customer customer = event.getTableView().getItems().get(event.getTablePosition().getRow());
                    String newValue = event.getNewValue();
                    String errorMessage = "";
                    if (newValue == null
                        || newValue.length() == 0
                        || !CustomerAddDialogController.VALID_EMAIL_ADDRESS_REGEX.matcher(newValue).find()) {
                        errorMessage += "Not valid email address!\n";
                    }
                    if (errorMessage.length() == 0) {
                        customer.setEmail(newValue);
                    } else {
                        showError(errorMessage);
                    }
                }
        );

        phone.setCellFactory(TextFieldTableCell.forTableColumn());
        phone.setOnEditCommit(
                event -> {
                    Customer customer = event.getTableView().getItems().get(event.getTablePosition().getRow());
                    String newValue = event.getNewValue();
                    String errorMessage = "";
                    if (newValue == null
                        || newValue.length() == 0
                        || !CustomerAddDialogController.VALID_PHONE_NUMBER_REGEX.matcher(newValue).find()) {
                        errorMessage += "Not valid phone number!\n";
                    }
                    if (errorMessage.length() == 0) {
                        customer.setPhone(newValue);
                    } else {
                        showError(errorMessage);
                    }
                }
        );
    }

    public void init() {
        table.setItems(customerList.getCustomerList());
    }

    public static CustomerList getCustomerList() {
        return customerList;
    }

    private void showError(String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(this.table.getScene().getWindow());
        alert.setTitle("Invalid Fields");
        alert.setHeaderText("Please correct invalid fields");
        alert.setContentText(errorMessage);

        alert.showAndWait();
    }

    public static boolean openCustomerAddDialog(Customer customer, Window owner) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("fxml/customerAddDialog.fxml"));
        Parent root = loader.load();

        Stage dialogStage = new Stage();
        dialogStage.setTitle("Edit Room");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(owner);
        Scene scene = new Scene(root);
        dialogStage.setScene(scene);

        CustomerAddDialogController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setCustomer(customer);

        dialogStage.showAndWait();
        return controller.isOkClicked();
    }



    @FXML
    private void handleAddButton() throws IOException {
        Customer tempCustomer = new Customer("firstname", "lastname", "address", "email", "phone");
        if (openCustomerAddDialog(tempCustomer, table.getScene().getWindow()))
            customerList.addCustomer(tempCustomer);
    }

    @FXML
    private void handleDeleteButton() throws IOException {
        if (table.getSelectionModel().getSelectedItem() != null &&
            DeleteDialogController.openDeleteDialog(
                new Customer("firstname", "lastname", "address", "email", "phone"),
                table.getScene().getWindow())) {
            int selectedIndex = table.getSelectionModel().getSelectedIndex();
            table.getItems().remove(selectedIndex);
        }
    }
}

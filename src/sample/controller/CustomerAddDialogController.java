package sample.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.w3c.dom.Text;
import sample.model.Customer;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class CustomerAddDialogController implements Initializable {
    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;

    @FXML
    private TextField address;

    @FXML
    private TextField email;

    @FXML
    private TextField phone;

    private Stage dialogStage;
    private Customer customer;
    private boolean okClicked = false;

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static final Pattern VALID_PHONE_NUMBER_REGEX =
            Pattern.compile("(09|01[2|6|8|9])+([0-9]{8})\\b", Pattern.CASE_INSENSITIVE);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public boolean isOkClicked() {
        return this.okClicked;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
        this.dialogStage.setResizable(false);
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
        firstName.setText(customer.getFirstName());
        lastName.setText(customer.getLastName());
        address.setText(customer.getAddress());
        email.setText(customer.getEmail());
        phone.setText(customer.getPhone());
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (firstName.getText() == null || firstName.getText().length() == 0) {
            errorMessage += "Not valid first name!\n";
        }
        if (lastName.getText() == null || lastName.getText().length() == 0) {
            errorMessage += "Not valid last name!\n";
        }
        if (address.getText() == null || address.getText().length() == 0) {
            errorMessage += "Not valid address!\n";
        }
        if (email.getText() == null || email.getText().length() == 0
                                    || !VALID_EMAIL_ADDRESS_REGEX.matcher(email.getText()).find()) {
            errorMessage += "Not valid email!\n";
        }
        if (phone.getText() == null || phone.getText().length() == 0
                                    || !VALID_PHONE_NUMBER_REGEX.matcher(phone.getText()).find()) {
            errorMessage += "Not valid phone number!\n";
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

    public void addButtonHandler() {
        if (isInputValid()) {
            this.customer.setFirstName(firstName.getText());
            this.customer.setLastName(lastName.getText());
            this.customer.setAddress(address.getText());
            this.customer.setEmail(email.getText());
            this.customer.setPhone(phone.getText());

            okClicked = true;
            dialogStage.close();
        }
    }

    public void cancelButtonHandler() {
        this.dialogStage.close();
    }
}

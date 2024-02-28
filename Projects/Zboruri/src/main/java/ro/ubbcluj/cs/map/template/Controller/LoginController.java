package ro.ubbcluj.cs.map.template.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ro.ubbcluj.cs.map.template.Domain.Client;
import ro.ubbcluj.cs.map.template.Exception.ServiceException;
import ro.ubbcluj.cs.map.template.MyApplication;
import ro.ubbcluj.cs.map.template.Service.ServiceClient;
import ro.ubbcluj.cs.map.template.Service.ServiceFlight;
import ro.ubbcluj.cs.map.template.Service.ServiceTicket;
import ro.ubbcluj.cs.map.template.Utilities.MessageAlert;

import java.io.IOException;
import java.util.Objects;

public class LoginController {
    private ServiceClient serviceClient;
    private ServiceFlight serviceFlight;
    private ServiceTicket serviceTicket;
    @FXML
    private TextField usernameTextField;

    public void initController(ServiceClient serviceClient, ServiceFlight serviceFlight, ServiceTicket serviceTicket) {
        this.serviceClient = serviceClient;
        this.serviceFlight = serviceFlight;
        this.serviceTicket = serviceTicket;
    }

    public void handleLogin() {
        if (this.usernameTextField.getText().isEmpty()) {
            MessageAlert.showMessage(null, Alert.AlertType.ERROR, "Something went wrong!", "You need to input a username!");
            return;
        }

        try {
            String username = this.usernameTextField.getText();
            Client client = this.serviceClient.loginClient(username);

            FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(MyApplication.class.getResource("views/client-view.fxml")));
            Scene scene = new Scene(fxmlLoader.load());

            ClientController clientController = fxmlLoader.getController();
            clientController.initController(this.serviceFlight, this.serviceTicket, client);

            // Adding the controller as an observer.
            this.serviceTicket.addObserver(clientController);

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle(client.getName());
            stage.show();
        } catch (ServiceException | IOException e) {
            MessageAlert.showMessage(null, Alert.AlertType.ERROR, "Something went wrong!", e.getMessage());
        }
    }
}

package ro.ubbcluj.cs.map.template.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ro.ubbcluj.cs.map.template.Domain.Driver;
import ro.ubbcluj.cs.map.template.Domain.Person;
import ro.ubbcluj.cs.map.template.Exception.ServiceException;
import ro.ubbcluj.cs.map.template.MyApplication;
import ro.ubbcluj.cs.map.template.Service.ServiceDriver;
import ro.ubbcluj.cs.map.template.Service.ServicePerson;
import ro.ubbcluj.cs.map.template.Service.ServiceRequest;
import ro.ubbcluj.cs.map.template.Utilities.MessageAlert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LoginController {
    private ServicePerson servicePerson;
    private ServiceDriver serviceDriver;
    private ServiceRequest serviceRequest;
    private final List<PersonController> personControllers = new ArrayList<>();
    private final List<DriverController> driverControllers = new ArrayList<>();

    @FXML
    private TextField usernameTextField;

    public void initController(ServicePerson servicePerson, ServiceDriver serviceDriver, ServiceRequest serviceRequest) {
        this.servicePerson = servicePerson;
        this.serviceDriver = serviceDriver;
        this.serviceRequest = serviceRequest;
    }

    private void openPersonInterface(Person person) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(MyApplication.class.getResource("views/person-view.fxml")));
        Scene scene = new Scene(fxmlLoader.load());

        PersonController personController = fxmlLoader.getController();
        personController.initController(person);

        this.personControllers.add(personController);
        driverControllers.forEach(driverController -> {
            personController.addObserver(driverController);
            driverController.addObserver(personController);
        });

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Client " + person.getName());
        stage.show();
    }

    private void openDriverInterface(Driver driver) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(MyApplication.class.getResource("views/driver-view.fxml")));
        Scene scene = new Scene(fxmlLoader.load());

        DriverController driverController = fxmlLoader.getController();
        driverController.initController(this.servicePerson, this.serviceDriver, this.serviceRequest, driver);

        this.driverControllers.forEach(driverController1 -> {
            driverController.addObserver(driverController1);
            driverController1.addObserver(driverController);
        });
        this.driverControllers.add(driverController);
        this.personControllers.forEach(personController -> {
            driverController.addObserver(personController);
            personController.addObserver(driverController);
        });

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Taximetrist " + driver.getName());
        stage.show();
    }

    public void handleLogin() throws IOException {
        // Checking for empty username text field.
        if (this.usernameTextField.getText().isEmpty()) {
            MessageAlert.showMessage(null, Alert.AlertType.ERROR, "Something went wrong!", "The username cannot be empty!");
            return;
        }

        String username = this.usernameTextField.getText();

        // Logging in the user based on its permissions (Person or Driver).
        if (this.servicePerson.isPerson(username)) {
            if (this.serviceDriver.isDriver(username)) {
                try {
                    Driver driver = this.serviceDriver.loginDriver(username);
                    this.openDriverInterface(driver);
                    return;
                } catch (ServiceException e) {
                    MessageAlert.showMessage(null, Alert.AlertType.ERROR, "Something went wrong!", e.getMessage());
                    return;
                }
            }

            // Logging in as person if the username doesn't correspond to a driver.
            try {
                Person person = this.servicePerson.loginPerson(username);
                this.openPersonInterface(person);
                return;
            } catch (ServiceException e) {
                MessageAlert.showMessage(null, Alert.AlertType.ERROR, "Something went wrong!", e.getMessage());
                return;
            }
        }

        MessageAlert.showMessage(null, Alert.AlertType.WARNING, "Something went wrong!", "No user found!");
    }
}

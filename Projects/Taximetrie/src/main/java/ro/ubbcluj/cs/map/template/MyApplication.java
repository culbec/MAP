package ro.ubbcluj.cs.map.template;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ro.ubbcluj.cs.map.template.Controller.LoginController;
import ro.ubbcluj.cs.map.template.Repository.DriverDBRepository;
import ro.ubbcluj.cs.map.template.Repository.PersonDBRepository;
import ro.ubbcluj.cs.map.template.Repository.RequestDBRepository;
import ro.ubbcluj.cs.map.template.Service.ServiceDriver;
import ro.ubbcluj.cs.map.template.Service.ServicePerson;
import ro.ubbcluj.cs.map.template.Service.ServiceRequest;

import java.io.IOException;
import java.util.Objects;

public class MyApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException, InterruptedException {
        Thread.sleep(1000);

        PersonDBRepository personDBRepository = new PersonDBRepository();
        DriverDBRepository driverDBRepository = new DriverDBRepository();
        RequestDBRepository requestDBRepository = new RequestDBRepository();

        ServicePerson servicePerson = new ServicePerson(personDBRepository);
        ServiceDriver serviceDriver = new ServiceDriver(driverDBRepository);
        ServiceRequest serviceRequest = new ServiceRequest(requestDBRepository);

        FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(MyApplication.class.getResource("views/login-view.fxml")));
        Scene scene = new Scene(fxmlLoader.load());

        LoginController loginController = fxmlLoader.getController();
        loginController.initController(servicePerson, serviceDriver, serviceRequest);

        stage.setScene(scene);
        stage.setTitle("Login");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
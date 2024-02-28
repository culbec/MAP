package ro.ubbcluj.cs.map.template;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ro.ubbcluj.cs.map.template.Controller.LoginController;
import ro.ubbcluj.cs.map.template.Repository.ClientDBRepository;
import ro.ubbcluj.cs.map.template.Repository.FlightDBRepository;
import ro.ubbcluj.cs.map.template.Repository.TicketDBRepository;
import ro.ubbcluj.cs.map.template.Service.ServiceClient;
import ro.ubbcluj.cs.map.template.Service.ServiceFlight;
import ro.ubbcluj.cs.map.template.Service.ServiceTicket;

import java.io.IOException;

public class MyApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        ClientDBRepository clientDBRepository = new ClientDBRepository();
        FlightDBRepository flightDBRepository = new FlightDBRepository();
        TicketDBRepository ticketDBRepository = new TicketDBRepository();

        ServiceClient serviceClient = new ServiceClient(clientDBRepository);
        ServiceFlight serviceFlight = new ServiceFlight(flightDBRepository);
        ServiceTicket serviceTicket = new ServiceTicket(ticketDBRepository);

        FXMLLoader fxmlLoader = new FXMLLoader(MyApplication.class.getResource("views/login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        LoginController loginController = fxmlLoader.getController();
        loginController.initController(serviceClient, serviceFlight, serviceTicket);


        stage.setTitle("Wizz Air");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
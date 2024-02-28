package ro.ubbcluj.cs.map.template;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ro.ubbcluj.cs.map.template.Controller.LoginController;
import ro.ubbcluj.cs.map.template.Repository.CityDBRepository;
import ro.ubbcluj.cs.map.template.Repository.TicketDBRepository;
import ro.ubbcluj.cs.map.template.Repository.TrainStationDBRepository;
import ro.ubbcluj.cs.map.template.Service.ServiceCity;
import ro.ubbcluj.cs.map.template.Service.ServiceTicket;
import ro.ubbcluj.cs.map.template.Service.ServiceTrainStation;

import java.io.IOException;

public class MyApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        CityDBRepository cityDBRepository = new CityDBRepository();
        TrainStationDBRepository trainStationDBRepository = new TrainStationDBRepository();
        TicketDBRepository ticketDBRepository = new TicketDBRepository();

        ServiceCity serviceCity = new ServiceCity(cityDBRepository);
        ServiceTrainStation serviceTrainStation = new ServiceTrainStation(trainStationDBRepository);
        ServiceTicket serviceTicket = new ServiceTicket(ticketDBRepository);

        FXMLLoader fxmlLoader = new FXMLLoader(MyApplication.class.getResource("views/login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        LoginController loginController = fxmlLoader.getController();
        loginController.initController(serviceCity, serviceTrainStation, serviceTicket);

        stage.setTitle("CFR");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
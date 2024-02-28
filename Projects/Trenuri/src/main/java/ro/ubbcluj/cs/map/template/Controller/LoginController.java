package ro.ubbcluj.cs.map.template.Controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ro.ubbcluj.cs.map.template.MyApplication;
import ro.ubbcluj.cs.map.template.Service.ServiceCity;
import ro.ubbcluj.cs.map.template.Service.ServiceTicket;
import ro.ubbcluj.cs.map.template.Service.ServiceTrainStation;

import java.io.IOException;
import java.util.Objects;

public class LoginController {
    private ServiceCity serviceCity;
    private ServiceTrainStation serviceTrainStation;
    private ServiceTicket serviceTicket;

    public void initController(ServiceCity serviceCity, ServiceTrainStation serviceTrainStation, ServiceTicket serviceTicket) {
        this.serviceCity = serviceCity;
        this.serviceTrainStation = serviceTrainStation;
        this.serviceTicket = serviceTicket;
    }

    public void handleNewClientWindow() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(MyApplication.class.getResource("views/client-view.fxml")));
        Scene scene = new Scene(fxmlLoader.load());

        ClientController clientController = fxmlLoader.getController();
        clientController.initController(this.serviceCity, this.serviceTrainStation, this.serviceTicket);
        this.serviceTrainStation.addObserver(clientController);

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("CFR - Client");
        stage.show();
    }
}

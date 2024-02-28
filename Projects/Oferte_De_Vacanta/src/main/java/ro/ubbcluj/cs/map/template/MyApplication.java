package ro.ubbcluj.cs.map.template;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ro.ubbcluj.cs.map.template.Controller.ClientController;
import ro.ubbcluj.cs.map.template.Controller.LocationController;
import ro.ubbcluj.cs.map.template.Domain.Client;
import ro.ubbcluj.cs.map.template.Exception.ServiceException;
import ro.ubbcluj.cs.map.template.Repository.*;
import ro.ubbcluj.cs.map.template.Service.*;

import java.io.IOException;

public class MyApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        ClientDBRepository clientDBRepository = new ClientDBRepository();
        LocationDBRepository locationDBRepository = new LocationDBRepository();
        HotelDBRepository hotelDBRepository = new HotelDBRepository();
        SpecialOfferDBRepository specialOfferDBRepository = new SpecialOfferDBRepository();
        ReservationDBRepository reservationDBRepository = new ReservationDBRepository();

        ServiceClient serviceClient = new ServiceClient(clientDBRepository);
        ServiceLocation serviceLocation = new ServiceLocation(locationDBRepository);
        ServiceHotel serviceHotel = new ServiceHotel(hotelDBRepository);
        ServiceReservation serviceReservation = new ServiceReservation(reservationDBRepository);
        ServiceSpecialOffer serviceSpecialOffer = new ServiceSpecialOffer(specialOfferDBRepository);

        for(long i = 0; i < 6; i++) {
            try {
                Client client = serviceClient.getClient(i);

                FXMLLoader clientLoader = new FXMLLoader(MyApplication.class.getResource("views/client-view.fxml"));
                Scene clientScene = new Scene(clientLoader.load());

                ClientController clientController = clientLoader.getController();
                clientController.initController(client, serviceClient, serviceSpecialOffer, serviceHotel, serviceLocation, serviceReservation);

                serviceReservation.addObserver(clientController);

                Stage clientStage = new Stage();
                clientStage.setTitle(client.getName());
                clientStage.setScene(clientScene);
                clientStage.show();
            } catch (ServiceException e) {
                System.err.println(e.getMessage());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        FXMLLoader fxmlLoader = new FXMLLoader(MyApplication.class.getResource("views/location-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        LocationController locationController = fxmlLoader.getController();
        locationController.initController(serviceLocation, serviceHotel, serviceSpecialOffer);

        stage.setTitle("Booking");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
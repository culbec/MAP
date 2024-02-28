package ro.ubbcluj.cs.map.template.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import ro.ubbcluj.cs.map.template.Domain.Hotel;
import ro.ubbcluj.cs.map.template.Domain.Location;
import ro.ubbcluj.cs.map.template.MyApplication;
import ro.ubbcluj.cs.map.template.Service.ServiceHotel;
import ro.ubbcluj.cs.map.template.Service.ServiceLocation;
import ro.ubbcluj.cs.map.template.Service.ServiceSpecialOffer;

import java.io.IOException;

public class LocationController {
    @FXML
    private ComboBox<Location> locationComboBox;
    @FXML
    private TableView<Hotel> hotelTableView;
    @FXML
    private TableColumn<Hotel, String> hotelNameTableColumn;
    @FXML
    private TableColumn<Hotel, Integer> noRoomsTableColumn;
    @FXML
    private TableColumn<Hotel, Integer> priceTableColumn;
    @FXML
    private TableColumn<Hotel, String> typeTableColumn;
    private ServiceLocation serviceLocation;
    private ServiceHotel serviceHotel;
    private ServiceSpecialOffer serviceSpecialOffer;

    public void initController(ServiceLocation serviceLocation, ServiceHotel serviceHotel, ServiceSpecialOffer serviceSpecialOffer) {
        this.serviceLocation = serviceLocation;
        this.serviceHotel = serviceHotel;
        this.serviceSpecialOffer = serviceSpecialOffer;

        this.hotelTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        this.hotelNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("hotelName"));
        this.noRoomsTableColumn.setCellValueFactory(new PropertyValueFactory<>("noRooms"));
        this.priceTableColumn.setCellValueFactory(new PropertyValueFactory<>("pricePerNight"));
        this.typeTableColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

        this.locationComboBox.getItems().addAll(this.serviceLocation.getLocations());

        this.hotelTableView.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                FXMLLoader fxmlLoader = new FXMLLoader(MyApplication.class.getResource("views/special-offer-view.fxml"));
                try {
                    Scene scene = new Scene(fxmlLoader.load());

                    SpecialOfferController specialOfferController = fxmlLoader.getController();
                    specialOfferController.initController(newValue, this.serviceSpecialOffer);

                    Stage stage = new Stage();
                    stage.setTitle("Special Offers");
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public void handleLocation() {
        Location location = this.locationComboBox.getSelectionModel().getSelectedItem();
        this.hotelTableView.getItems().clear();
        this.hotelTableView.getItems().addAll(this.serviceHotel.getHotelsOfLocation(location.getLocationId()));
    }
}

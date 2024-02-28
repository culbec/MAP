package ro.ubbcluj.cs.map.template.Controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import ro.ubbcluj.cs.map.template.Domain.Client;
import ro.ubbcluj.cs.map.template.Domain.Hotel;
import ro.ubbcluj.cs.map.template.Domain.SpecialOffer;
import ro.ubbcluj.cs.map.template.Domain.SpecialOfferDTO;
import ro.ubbcluj.cs.map.template.Exception.ServiceException;
import ro.ubbcluj.cs.map.template.Service.*;
import ro.ubbcluj.cs.map.template.Utilities.Constants;
import ro.ubbcluj.cs.map.template.Utilities.Event.EventType;
import ro.ubbcluj.cs.map.template.Utilities.Event.ReservationEvent;
import ro.ubbcluj.cs.map.template.Utilities.MessageAlert;
import ro.ubbcluj.cs.map.template.Utilities.Observer.Observer;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class ClientController implements Observer<ReservationEvent> {
    @FXML
    private TableView<SpecialOfferDTO> specialOfferTableView;
    @FXML
    private TableColumn<SpecialOfferDTO, String> hotelTableColumn;
    @FXML
    private TableColumn<SpecialOfferDTO, String> locatieTableColumn;
    @FXML
    private TableColumn<SpecialOfferDTO, String> startDateTableColumn;
    @FXML
    private TableColumn<SpecialOfferDTO, String> endDateTableColumn;
    @FXML
    private TextField noNightsTextField;
    @FXML
    private HBox reservationLabelHBox;

    private ServiceClient serviceClient;
    private ServiceSpecialOffer serviceSpecialOffer;
    private ServiceHotel serviceHotel;
    private ServiceLocation serviceLocation;
    private ServiceReservation serviceReservation;
    private Client client;

    public void initController(Client client, ServiceClient serviceClient, ServiceSpecialOffer serviceSpecialOffer, ServiceHotel serviceHotel, ServiceLocation serviceLocation, ServiceReservation serviceReservation) {
        this.client = client;
        this.serviceClient = serviceClient;
        this.serviceSpecialOffer = serviceSpecialOffer;
        this.serviceHotel = serviceHotel;
        this.serviceLocation = serviceLocation;
        this.serviceReservation = serviceReservation;

        this.specialOfferTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        this.hotelTableColumn.setCellValueFactory(spoto -> new SimpleStringProperty(spoto.getValue().getHotel().getHotelName()));
        this.locatieTableColumn.setCellValueFactory(spoto -> new SimpleStringProperty(spoto.getValue().getLocation().getLocationName()));
        this.startDateTableColumn.setCellValueFactory(spoto -> new SimpleStringProperty(spoto.getValue().getSpecialOffer().getStartDate().toString()));
        this.endDateTableColumn.setCellValueFactory(spoto -> new SimpleStringProperty(spoto.getValue().getSpecialOffer().getEndDate().toString()));

        this.specialOfferTableView.getItems().addAll(this.serviceSpecialOffer.getSpecialOffersForClient(client).stream().map(specialOffer -> {
            Hotel hotel = this.serviceHotel.getHotel(specialOffer.getHotelId());
            return new SpecialOfferDTO(specialOffer, hotel, this.serviceLocation.getLocation(hotel.getLocationId()));
        }).toList());
    }

    public void handleBook() {
        if (this.specialOfferTableView.getSelectionModel().getSelectedItem() == null) {
            MessageAlert.showMessage(null, Alert.AlertType.ERROR, "Something went wrong!", "You need to select a special offer!");
            return;
        }

        if (this.noNightsTextField.getText().isEmpty() || !this.noNightsTextField.getText().matches("[0-9]+")) {
            MessageAlert.showMessage(null, Alert.AlertType.ERROR, "Something went wrong!", "You need to enter a valid number of nights!");
            return;
        }

        SpecialOffer specialOffer = this.specialOfferTableView.getSelectionModel().getSelectedItem().getSpecialOffer();
        Hotel hotel = this.specialOfferTableView.getSelectionModel().getSelectedItem().getHotel();

        try {
            Long clientId = this.client.getClientId();
            Double hotelId = hotel.getHotelId();
            LocalDateTime startDate = specialOffer.getStartDate().atStartOfDay();
            int noNights = Integer.parseInt(this.noNightsTextField.getText());

            this.serviceReservation.reserveRoom(clientId, hotelId, startDate, noNights);
        } catch (ServiceException e) {
            MessageAlert.showMessage(null, Alert.AlertType.ERROR, "Something went wrong!", e.getMessage());
        }
    }

    @Override
    public void update(ReservationEvent reservationEvent) {
        if (reservationEvent.getEventType().equals(EventType.RESERVATION_ADDED)) {
            Client _client = this.serviceClient.getClient(reservationEvent.getReservation().getClientId());

            if (!_client.getClientId().equals(this.client.getClientId()) && _client.getHobby().equals(this.client.getHobby())) {
                this.reservationLabelHBox.getChildren().clear();
                this.reservationLabelHBox.getChildren().add(new Label(Constants.RESERVATION_LABEL));
            }
        }
    }
}
